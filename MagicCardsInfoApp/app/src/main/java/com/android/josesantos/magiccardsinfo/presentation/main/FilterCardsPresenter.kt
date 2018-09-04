package com.android.josesantos.magiccardsinfo.presentation.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.android.josesantos.magiccardsinfo.data.api.LanguageConstants
import com.android.josesantos.magiccardsinfo.data.entity.MagicApiCard
import com.android.josesantos.magiccardsinfo.data.entity.MagicApiResponse
import com.android.josesantos.magiccardsinfo.model.repository.MagicApiRepositoryImpl
import com.android.josesantos.magiccardsinfo.util.RunOn
import com.android.josesantos.magiccardsinfo.util.SchedulerType
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject

/**
 * Created by josesantos on 24/03/18.
 */
class FilterCardsPresenter
@Inject constructor(private val magicApiRepository: MagicApiRepositoryImpl,
                    private val view: FilterCardsContracts.View) : FilterCardsContracts.Presenter, LifecycleObserver {

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    @Inject
    @field:RunOn(SchedulerType.IO)
    lateinit var io: Scheduler

    @Inject
    @field:RunOn(SchedulerType.UI)
    lateinit var ui: Scheduler

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {
        Log.d(FilterCardsPresenter::class.java.simpleName, "On Attach")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onDetach() {
        Log.d(FilterCardsPresenter::class.java.simpleName, "On Dettach")
    }

    private var cards: MutableList<MagicApiCard>? = null

    override fun searchCardName(cardName: String) {

        magicApiRepository.getMagicCardsByName(cardName)
                .subscribeOn(io)
                .observeOn(ui)
                .subscribe(object : SingleObserver<MagicApiResponse> {

                    override fun onSubscribe(d: Disposable) {
                        //todo add disposable composite to presenter
                    }

                    override fun onSuccess(magicApiResponse: MagicApiResponse) {
                        view.hideProgress()
                        cards = magicApiResponse.cards
                        view.onCardNamesResult(removeRepeateadResults(magicApiResponse))
                    }

                    override fun onError(e: Throwable) {
                        handleError(e)
                    }
                })
    }

    private fun handleError(e: Throwable) {
        view.hideProgress()
    }

    private fun removeRepeateadResults(apiResponse: MagicApiResponse): List<MagicApiCard> {
        val cardsResult = ArrayList<MagicApiCard>()

        apiResponse.cards.forEach { card: MagicApiCard? ->
            if (cardsResult.none { card?.name == it.name }) {
                cardsResult.add(card!!)
            }
        }

        if (cardsResult.isEmpty()) {
            val card = MagicApiCard()
            card.name = "No Results Found"
            cardsResult.add(card)
        }

        return cardsResult
    }

    override fun setEnglishLanguage() {
        magicApiRepository.setLanguage(LanguageConstants.EN_US)
    }

    override fun setPortugueseLanguage() {
        magicApiRepository.setLanguage(LanguageConstants.PT_BR)
    }
}