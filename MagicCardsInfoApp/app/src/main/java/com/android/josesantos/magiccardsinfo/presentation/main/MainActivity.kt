package com.android.josesantos.magiccardsinfo.presentation.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v7.widget.GridLayoutManager
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
import com.android.josesantos.magiccardsinfo.data.ligamagic.MagicInfoParser
import com.android.josesantos.magiccardsinfo.presentation.CardsPagerAdapter
import com.android.josesantos.magiccardsinfo.presentation.LojasAdapter
import com.android.josesantos.magiccardsinfo.presentation.base.BaseActivity
import com.android.josesantos.magiccardsinfo.presentation.main.di.DaggerMainComponent
import com.android.josesantos.magiccardsinfo.presentation.main.di.MainPresenterModule
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Created by josesantos on 26/02/18.
 */
class MainActivity : BaseActivity(), MainContracts.View {
    internal var cards: List<String> = ArrayList()
    private var popupWindow: PopupWindow? = null
    private var queryHandler: Handler? = null
    private var searchRunnable: Runnable? = null
    private var queryValue: String? = null
    private val TAG = MainActivity::class.java.simpleName
    private val TIME = "TIME"
    val stores = mutableListOf<LojaInfo>()
    var filteredStores = mutableListOf<LojaInfo>()
    val editions = mutableListOf<String>()
    val conditions = mutableListOf<String>()
    val storeName = mutableListOf<String>()

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        configurePopWindow()
        configureQuery()
        configureRecycler()
        configureViewPager()

        initializePresenter()

    }

    private fun initializePresenter() {
        DaggerMainComponent.builder()
                .mainPresenterModule(MainPresenterModule(this))
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


            presenter.getSelectedCards(adapterView.adapter.getItem(i).toString())

            val query = adapterView.adapter.getItem(i).toString()

            startQuery(query)

            popupWindow!!.dismiss()

            hideKeyboard()
        }

        popupWindow!!.setHeight(resultsList.size * 150)
        if (popupWindow!!.getHeight() > 600) {
            popupWindow!!.setHeight(600)
        }

        popupWindow!!.setContentView(listView)

        popupWindow!!.showAsDropDown(busca)


    }

    private fun hideKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun configurePopWindow() {
        popupWindow = PopupWindow(this)
        popupWindow!!.setBackgroundDrawable(getDrawable(R.drawable.search_background))
        popupWindow!!.setOutsideTouchable(true)

    }

    private fun startQuery(query: String) {
        queryValue = query

        lojas_recycler.visibility = View.GONE
        progress_stores.visibility = View.VISIBLE

        val thread = HandlerThread("ligamagic-thread")
        thread.start()

        val handler = Handler(thread.looper)
        handler.post { this.getLigamagicPage() }
    }

    override fun showCards(cardsList: MutableList<out MagicApiCard>?) {
        cardsList?.let {
            showContent(it.toList())
        }
    }

    private fun getLigamagicPage() {
        try {

            stores.clear()
            editions.clear()
            conditions.clear()
            storeName.clear()

            val startTime = System.currentTimeMillis() / 1000L
            Log.d(TIME, "START: " + startTime.toString())

            val ligamagicUrl = "http://www.ligamagic.com.br/?view=cards/card&card="
            val scgUrl = "http://sales.starcitygames.com/search.php?substring="

            val doc1 = Jsoup.connect(ligamagicUrl + queryValue).get()

            val startElemTime = System.currentTimeMillis() / 1000L
            Log.d(TIME, "START GET ELEMENTS: " + startElemTime.toString() + " TOTAL: " + (startElemTime - startTime))

//            val scriptElements = doc1.getElementsByTag("script")
//            val elements = doc1.getElementsByClass("box p10")
//            val elements1 = doc1.getElementById("card-principal") //id card-info
            val elements2 = doc1.getElementsByAttributeValueContaining("class", "estoque-linha")

            elements2.forEach {
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
                    loja.nome = it1.getElementsByAttribute("src").attr("title")
                    if (!storeName.contains(loja.nome)) {
                        storeName.add(loja.nome)
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

                stores.add(loja)
            }

            val endFOrEachTime = System.currentTimeMillis() / 1000L
            Log.d(TIME, "END FOR EACH ELEMENTS: " + endFOrEachTime.toString() + " TOTAL: " + (endFOrEachTime - startElemTime))
            Log.d(TIME, "TOTAL TIME: " + (endFOrEachTime - startTime))

            runOnUiThread {
                showRecycler(stores)
            }

//            val lojasInfoParser = LojasInfoParser()
//            lojasInfoParser.parse(doc1)

//            for (element in scriptElements) {
//                for (node in element.dataNodes()) {
//                    Log.d(TAG, "node: " + node.wholeData)
//                    if (node.wholeData.contains("VETiRaridade")) {
//
//                        val parser = MagicInfoParser()
//                        parser.parse(node.wholeData)
//
//                        val lojasInfoParser1 = LojasInfoParser()
//                        lojasInfoParser1.parse(doc1)
//
//                        //                        loadController++;
//                        //
//                        //                        continueLoadind();
//
//                        runOnUiThread {
//                            showContent(parser)
//                            showRecycler(lojasInfoParser1)
//                        }
//
//                    }
//                }
//            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    private fun showContent(parser: MagicInfoParser) {
        val cardsPagerAdapter = CardsPagerAdapter(supportFragmentManager)
        cardsPagerAdapter.setListCards(parser.listOfCards)

        hideProgress()

        vp_card_versions.setAdapter(cardsPagerAdapter)
        vp_card_versions.getAdapter().notifyDataSetChanged()

        view_pager_indicator.setViewPager(vp_card_versions)
        view_pager_indicator.setVisibility(View.VISIBLE)
    }

    private fun showContent(parser: List<MagicApiCard>) {
        val cardsPagerAdapter = CardsPagerAdapter(supportFragmentManager)
        cardsPagerAdapter.seApiListCards(parser)

        hideProgress()

        vp_card_versions.setAdapter(cardsPagerAdapter)
        vp_card_versions.getAdapter().notifyDataSetChanged()

        view_pager_indicator.setViewPager(vp_card_versions)
        view_pager_indicator.setVisibility(View.VISIBLE)
    }

    private fun showRecycler(lojas: List<LojaInfo>) {
        progress_stores.visibility = View.GONE
        lojas_recycler.visibility = View.VISIBLE

        (lojas_recycler.getAdapter() as LojasAdapter).setListLojas(lojas)
        lojas_recycler.getAdapter().notifyDataSetChanged()

        if (lojas.isEmpty()) {
            noResultFound()
        }
    }

    private fun configureViewPager() {
        vp_card_versions.adapter = CardsPagerAdapter(supportFragmentManager)
    }

    private fun configureRecycler() {
        lojas_recycler.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        lojas_recycler.adapter = LojasAdapter(ArrayList<LojaInfo>(), this)
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

        busca.setText("Goblin Lore")
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
        progress.setVisibility(View.VISIBLE)
        vp_card_versions.setVisibility(View.INVISIBLE)
        tv_not_found.setVisibility(View.GONE)

    }

    override fun hideProgress() {
        progress.setVisibility(View.GONE)
        vp_card_versions.setVisibility(View.VISIBLE)
    }

    private fun noResultFound() {
        tv_not_found.setVisibility(View.VISIBLE)
        vp_card_versions.setVisibility(View.INVISIBLE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.en_us -> {
                presenter.setEnglishLanguage()
            }
            R.id.pt_br -> {
                presenter.setPortugueseLanguage()
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
            R.id.clear_filter ->{
                clearFilters()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showStoreDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Selecione Loja")
        alert.setItems(storeName.toTypedArray()) { _, p1 ->
            filterByStore(storeName[p1])
        }
        alert.create().show()
    }

    private fun clearFilters() {
        filteredStores.clear()
        showRecycler(stores)
    }

    private fun showEditionDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Selecione Edição")
        alert.setItems(editions.toTypedArray()) { _, p1 ->
            filterByEdition(editions[p1])
        }
        alert.create().show()
    }

    private fun filterByEdition(edition: String) {
        filteredStores = stores.filter {
            it.edition == edition
        }.toMutableList()

        showRecycler(filteredStores)
    }

    //todo merge filters
    private fun filterByStore(store: String) {
        filteredStores = stores.filter {
            it.nome == store
        }.toMutableList()

        showRecycler(filteredStores)
    }

    private fun showConditionDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Selecione Condição")
        alert.setItems(conditions.toTypedArray()) { _, p1 ->
            filterByCondition(conditions[p1])
        }
        alert.create().show()
    }

    private fun filterByCondition(condition: String) {
        filteredStores = stores.filter {
            it.condition == condition
        }.toMutableList()

        showRecycler(filteredStores)
    }
}