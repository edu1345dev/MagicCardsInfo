package com.android.josesantos.magiccardsinfo.presentation.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.android.josesantos.magiccardsinfo.MagicApplication
import com.android.josesantos.magiccardsinfo.R
import com.android.josesantos.magiccardsinfo.data.ligamagic.LojaInfo
import com.android.josesantos.magiccardsinfo.data.ligamagic.LojasInfoParser
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

    override fun onCardNamesResult(cardNames: MutableList<String>?) {
        showPopWindow(cardNames!!)
    }

    private fun showPopWindow(resultsList: List<String>) {
        val arrayString = resultsList.toTypedArray()

        val listView = ListView(this)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayString)
        listView.setOnItemClickListener { adapterView, view, i, l ->

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

        showProgress()

        val thread = HandlerThread("thread")
        thread.start()

        val handler = Handler(thread.looper)
        handler.post { this.getLigamagicPage() }
    }

    private fun getLigamagicPage() {
        try {

            val ligamagicUrl = "http://www.ligamagic.com.br/?view=cards/card&card="
            val scgUrl = "http://sales.starcitygames.com/search.php?substring="

            val doc1 = Jsoup.connect(ligamagicUrl + queryValue).get()

            Log.d(TAG, "doc: " + doc1.html())

            val scriptElements = doc1.getElementsByTag("script")

            val lojasInfoParser = LojasInfoParser()
            lojasInfoParser.parse(doc1)

            for (element in scriptElements) {
                for (node in element.dataNodes()) {
                    Log.d(TAG, "node: " + node.wholeData)
                    if (node.wholeData.contains("VETiRaridade")) {

                        val parser = MagicInfoParser()
                        parser.parse(node.wholeData)

                        val lojasInfoParser1 = LojasInfoParser()
                        lojasInfoParser1.parse(doc1)

                        //                        loadController++;
                        //
                        //                        continueLoadind();

                        runOnUiThread {
                            showContent(parser)
                            showRecycler(lojasInfoParser1)
                        }

                    }
                }
            }

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

    private fun showRecycler(parser: LojasInfoParser) {
        (lojas_recycler.getAdapter() as LojasAdapter).setListLojas(parser.getLojasInfo())
        lojas_recycler.getAdapter().notifyDataSetChanged()

        if (parser.lojasInfo.size == 0) {
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

        busca.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                requestCardsFromApi(busca.getText().toString())

                return@setOnEditorActionListener false
            }
            false
        })

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
}