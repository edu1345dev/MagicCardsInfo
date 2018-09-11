package com.android.josesantos.magiccardsinfo.presentation.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.android.josesantos.magiccardsinfo.MagicApplication
import com.android.josesantos.magiccardsinfo.R
import com.android.josesantos.magiccardsinfo.data.entity.MagicApiCard
import com.android.josesantos.magiccardsinfo.data.ligamagic.LojaInfo
import com.android.josesantos.magiccardsinfo.presentation.LojasAdapter
import com.android.josesantos.magiccardsinfo.presentation.SimpleListAdapter
import com.android.josesantos.magiccardsinfo.presentation.base.BaseActivity
import com.android.josesantos.magiccardsinfo.presentation.main.di.DaggerFilterCardsComponent
import com.android.josesantos.magiccardsinfo.presentation.main.di.FilterCardsPresenterModule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_filter_cards.*
import org.jsoup.Jsoup
import java.io.IOException
import javax.inject.Inject

/**
 * Created by josesantos on 26/02/18.
 */
class FilterCardsActivity : BaseActivity(), FilterCardsContracts.View {
    private var popupWindow: PopupWindow? = null
    private var queryHandler: Handler? = null
    private var searchRunnable: Runnable? = null
    private val TAG = FilterCardsActivity::class.java.simpleName
    private val TIME = "TIME"
    private var storesInfo = mutableListOf<LojaInfo>()
    private var filteredStores = mutableListOf<LojaInfo>()
    private val editions = mutableListOf<String>()
    private val conditions = mutableListOf<String>()
    private val stores = mutableListOf<String>()
    private var editionFilter = ""
    private var conditionFilter = ""
    private var storeFilter = ""
    private val gson = Gson()

    @Inject
    lateinit var presenter: FilterCardsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_filter_cards)
        configurePopWindow()
        configureQuery()
        configureRecycler()
        initFilters()

        initializePresenter()

    }

    private fun saveCardsList(){
        getSharedPreferences(this.packageName, Context.MODE_PRIVATE).edit().putString("cards", gson.toJson(cardsNameAdapter.list)).apply()
    }

    private fun recoverCardsList(){
        val cards = getSharedPreferences(this.packageName, Context.MODE_PRIVATE).getString("cards", "")

        val listType = object : TypeToken<List<String>>() {
        }.type

        val list = gson.fromJson<List<String>>(cards, listType )

        cardsNameAdapter.setCardsList(list)
    }

    private fun initFilters() {
        tv_store_filter.setOnClickListener {
            storeFilter = ""
            filtersVisibility()
            filterStores()
        }

        tv_condition_filter.setOnClickListener {
            conditionFilter = ""
            filtersVisibility()
            filterStores()
        }

        tv_edition_filter.setOnClickListener {
            editionFilter = ""
            filtersVisibility()
            filterStores()
        }
    }

    private fun initializePresenter() {
        DaggerFilterCardsComponent.builder()
                .filterCardsPresenterModule(FilterCardsPresenterModule(this))
                .mainRepositoryComponent(MagicApplication.getInstance().mainRepositoryComponent)
                .build()
                .inject(this)

    }

    override fun onCardNamesResult(cardNames: MutableList<MagicApiCard>?) {
        showPopWindow(cardNames!!)
    }

    private fun showPopWindow(resultsList: List<MagicApiCard>) {
        val arrayString = resultsList.toTypedArray()

        val listView = ListView(this)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayString)
        listView.setOnItemClickListener { adapterView, view, i, l ->

            addCardToWantList(adapterView.adapter.getItem(i).toString())

            popupWindow!!.dismiss()

            hideKeyboard()
        }

        popupWindow!!.height = resultsList.size * 150
        if (popupWindow!!.height > 600) {
            popupWindow!!.height = 600
        }

        popupWindow!!.contentView = listView

        popupWindow!!.showAsDropDown(busca)


    }

    private fun addCardToWantList(cardName: String) {
        if (!cardsNameAdapter.list.contains(cardName)) {
            cardsNameAdapter.addCard(cardName)
            cardsNameAdapter.notifyDataSetChanged()
        }
    }

    private fun hideKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun configurePopWindow() {
        popupWindow = PopupWindow(this)
        popupWindow!!.setBackgroundDrawable(getDrawable(R.drawable.search_background))
        popupWindow!!.isOutsideTouchable = true

    }

    private fun startQuery() {
        lojas_recycler.visibility = View.INVISIBLE
        progress_stores.visibility = View.VISIBLE

        val thread = HandlerThread("ligamagic-thread")
        thread.start()

        val handler = Handler(thread.looper)
        handler.post { this.getLigamagicPage() }
    }

    private fun getLigamagicPage() {
        try {

            storesInfo.clear()
            editions.clear()
            conditions.clear()
            stores.clear()

            val startTime = System.currentTimeMillis() / 1000L
            Log.d(TIME, "START: " + startTime.toString())

            val ligamagicUrl = "http://www.ligamagic.com.br/?view=cards/card&card="
            val scgUrl = "http://sales.starcitygames.com/search.php?substring="

            var queryList = cardsNameAdapter.list

//            val doc1 = Jsoup.connect(ligamagicUrl + queryValue).get()
//            val doc2 = Jsoup.connect(ligamagicUrl + "Lightning Bolt").get()
//            val doc3 = Jsoup.connect(ligamagicUrl + "Delver of Secrets").get()
//            val doc4 = Jsoup.connect(ligamagicUrl + "Overgrown Tomb").get()
            val startElemTime = System.currentTimeMillis() / 1000L

            val cardsMapAux = hashMapOf<String, MutableList<LojaInfo>>()

            queryList.forEach { cardName: String ->

                val doc = Jsoup.connect(ligamagicUrl + cardName).get()

//            Log.d(TIME, "START GET ELEMENTS: " + startElemTime.toString() + " TOTAL: " + (startElemTime - startTime))
                var storeAux: MutableList<LojaInfo> = mutableListOf()

                val elements = doc.getElementsByAttributeValueContaining("class", "estoque-linha")
                elements.forEach {

                    if (it.getElementsByClass("l-preco").text().isNotEmpty()) {
                        //todo do something for leiloes
                        it.getElementsByClass("l-preco").text()
                        it.getElementsByClass("l-preco-aux").text()
                        return@forEach
                    }

                    val loja = LojaInfo()
                    val value = it.getElementsByClass("e-col3").text().replace("R$ ", "")
                    if (value.split(" ").size > 1) {
                        loja.price = "R$ " + value.split(" ")[0]
                        loja.promoPrice = "R$ " + value.split(" ")[1]
                    } else {
                        loja.price = "R$ " + value
                    }

                    loja.edition = it.getElementsByClass("e-mob-edicao-lbl").text()
                    if (!editions.contains(loja.edition)) {
                        editions.add(loja.edition)
                    }

                    it.getElementsByClass("e-col1").forEach { it1 ->
                        it1.getElementsByAttribute("src").attr("src")
                        loja.storeName = it1.getElementsByAttribute("src").attr("title")
                        if (!stores.contains(loja.storeName)) {
                            stores.add(loja.storeName)
                        }
                    }

                    loja.condition = it.getElementsByClass("e-col4 e-col4-offmktplace").text()
                    loja.condition?.let {
                        if (!conditions.contains(it)) {
                            conditions.add(it)
                        }
                    }

                    loja.qtd = it.getElementsByClass("e-col5 e-col5-offmktplace ").text()
                    it.getElementsByClass("e-col8 e-col8-offmktplace ").forEach { it3 ->
                        loja.lojaUrl = it3.getElementsByAttribute("href").attr("href")
                    }

                    loja.cardName = cardName

                    storeAux.add(loja)
                }

                cardsMapAux[cardName] = storeAux
            }

            val stores = mutableListOf<LojaInfo>()
            cardsMapAux.forEach { s, mutableList ->
                stores.addAll(mutableList)
            }

            stores.groupBy { it.storeName }

            val endFOrEachTime = System.currentTimeMillis() / 1000L
            Log.d(TIME, "END FOR EACH ELEMENTS: " + endFOrEachTime.toString() + " TOTAL: " + (endFOrEachTime - startElemTime))
            Log.d(TIME, "TOTAL TIME: " + (endFOrEachTime - startTime))

            storesInfo = stores

            runOnUiThread {
                showRecycler(getByStoreNameList(storesInfo))
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getByStoreNameList(stores: MutableList<LojaInfo>): MutableList<LojaInfo> {
        if (stores.isEmpty()){
            return mutableListOf()
        }

        val storeAux = mutableListOf<LojaInfo>()

        val storesSorted = stores.sortedBy { it.storeName }.toMutableList()
        val groupByStores = stores.groupBy { it.storeName }

        storesSorted.clear()

        groupByStores.values.sortedBy {
            it.size
        }.asReversed().forEach {
            it.forEach {
                storesSorted.add(it)
            }
        }

        val storeInit = LojaInfo()
        storeInit.storeName = storesSorted[0].storeName
        storeAux.add(storeInit)

        for (i in 0 until stores.size) {
            if (i + 1 < stores.size && storesSorted[i].storeName != storesSorted[i + 1].storeName) {
                val store = LojaInfo()
                store.storeName = storesSorted[i + 1].storeName

                storeAux.add(storesSorted[i])
                storeAux.add(store)
            } else {
                storeAux.add(storesSorted[i])
            }
        }

        return storeAux
    }

    private fun showRecycler(lojas: List<LojaInfo>) {
        progress_stores.visibility = View.GONE
        lojas_recycler.visibility = View.VISIBLE

        storesAdapter.setListLojas(lojas)
        storesAdapter.notifyDataSetChanged()

        if (lojas.isEmpty()) {
            noResultFound()
        }
    }

    private val storesAdapter = LojasAdapter(emptyList(), this)

    private lateinit var cardsNameAdapter: SimpleListAdapter

    private fun configureRecycler() {
//        lojas_recycler.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        cardsNameAdapter = SimpleListAdapter(mutableListOf(), this)
        rv_cards_name.layoutManager = LinearLayoutManager(this)
        rv_cards_name.adapter = cardsNameAdapter

//        cardsNameAdapter.setOnClickListener { _, _ ->
//            startQuery()
//        }

        lojas_recycler.layoutManager = LinearLayoutManager(this)
        lojas_recycler.adapter = storesAdapter
    }

    private fun configureQuery() {

        busca.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                requestCardsFromApi(busca.getText().toString())

                return@setOnEditorActionListener false
            }
            false
        }

        busca.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // do nothing
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //do nothing
            }

            override fun afterTextChanged(editable: Editable) {
                delayQueryStart()
            }
        })

        bt_search.setOnClickListener {
            startQuery()
        }
    }

    fun delayQueryStart() {
        if (queryHandler == null) {
            queryHandler = Handler()
        }

        if (searchRunnable != null) {
            queryHandler!!.removeCallbacks(searchRunnable)
        }

        queryHandler!!.postDelayed(getSearchRunnable(), 800)

    }

    private fun getSearchRunnable(): Runnable {
        searchRunnable = Runnable { requestCardsFromApi(busca.getText().toString()) }
        return searchRunnable!!
    }

    private fun requestCardsFromApi(query: String) {
        if (query == "") {
            return
        }

        showProgress()

        presenter.searchCardName(query)

    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
        tv_not_found.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun noResultFound() {
        tv_not_found.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.en_us -> {
//                presenter.setEnglishLanguage()
                saveCardsList()
            }
            R.id.pt_br -> {
//                presenter.setPortugueseLanguage()
                recoverCardsList()
            }
            R.id.condition -> {
                showConditionDialog()
            }
            R.id.edition -> {
                showEditionDialog()
            }
            R.id.store -> {
                showStoreDialog()
            }
            R.id.clear_filter -> {
                clearFilters()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearFilters() {
        storeFilter = ""
        conditionFilter = ""
        editionFilter = ""

        filteredStores.clear()
        filtersVisibility()

        showRecycler(getByStoreNameList(storesInfo))
    }

    private fun showStoreDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Loja")
        alert.setItems(stores.toTypedArray()) { _, p1 ->
            storeFilter = stores[p1]
            filtersVisibility()
            filterStores()
        }
        alert.create().show()
    }

    private fun showEditionDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Edição")
        alert.setItems(editions.toTypedArray()) { _, p1 ->
            editionFilter = editions[p1]
            filtersVisibility()
            filterStores()
        }
        alert.create().show()
    }

    private fun showConditionDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Condição")
        alert.setItems(conditions.toTypedArray()) { _, p1 ->
            conditionFilter = conditions[p1]
            filtersVisibility()
            filterStores()
        }
        alert.create().show()
    }

    private fun filtersVisibility() {
        if (conditionFilter.isNotEmpty()) {
            tv_condition_filter.visibility = View.VISIBLE
            tv_condition_filter.text = conditionFilter
        } else {
            tv_condition_filter.visibility = View.GONE
        }

        if (storeFilter.isNotEmpty()) {
            tv_store_filter.visibility = View.VISIBLE
            tv_store_filter.text = storeFilter
        } else {
            tv_store_filter.visibility = View.GONE
        }

        if (editionFilter.isNotEmpty()) {
            tv_edition_filter.visibility = View.VISIBLE
            tv_edition_filter.text = editionFilter
        } else {
            tv_edition_filter.visibility = View.GONE
        }
    }

    private fun filterStores() {
        tv_not_found.visibility = View.INVISIBLE

        filteredStores = if (storeFilter.isNotEmpty()) {
            storesInfo.filter {
                it.storeName == storeFilter
            }.toMutableList()
        } else {
            storesInfo
        }

        if (editionFilter.isNotEmpty()) {
            filteredStores = filteredStores.filter {
                it.edition == editionFilter
            }.toMutableList()
        }

        if (conditionFilter.isNotEmpty()) {
            filteredStores = filteredStores.filter {
                it.condition == conditionFilter
            }.toMutableList()
        }

        showRecycler(getByStoreNameList(filteredStores))
    }
}