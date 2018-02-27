package com.android.josesantos.magiccardsinfo.presentation.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.PopupWindow
import com.android.josesantos.magiccardsinfo.R
import com.android.josesantos.magiccardsinfo.data.ligamagic.LojaInfo
import com.android.josesantos.magiccardsinfo.presentation.CardsPagerAdapter
import com.android.josesantos.magiccardsinfo.presentation.LojasAdapter
import com.android.josesantos.magiccardsinfo.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by josesantos on 26/02/18.
 */
class MainActivityKotlin : BaseActivity() {

    internal var cards: List<String> = ArrayList()
    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        configurePopWindow()

    }

    private fun configurePopWindow() {
        popupWindow = PopupWindow(this)
        popupWindow!!.setBackgroundDrawable(getDrawable(R.drawable.search_background))
        popupWindow!!.setOutsideTouchable(true)

    }

    private fun configureViewPager() {
        vp_card_versions.adapter = CardsPagerAdapter(supportFragmentManager)
    }

    private fun configureRecycler() {
        lojas_recycler.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        lojas_recycler.adapter = LojasAdapter(ArrayList<LojaInfo>())
    }

    private fun configureQuery() {

        busca.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

//                requestCardsFromApi(etNomeCarta.getText().toString())

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
//                delayQueryStart()
            }
        })
    }
}