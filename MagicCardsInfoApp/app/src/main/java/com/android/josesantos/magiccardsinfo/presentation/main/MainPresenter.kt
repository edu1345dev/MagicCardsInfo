package com.android.josesantos.magiccardsinfo.presentation.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
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
class MainPresenter
@Inject constructor(private val magicApiRepository: MagicApiRepositoryImpl,
                    private val view: MainContracts.View) : MainContracts.Presenter, LifecycleObserver {

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
        Log.d(MainPresenter::class.java.simpleName, "On Attach")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onDetach() {
        Log.d(MainPresenter::class.java.simpleName, "On Dettach")
    }

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

    private fun removeRepeateadResults(apiResponse: MagicApiResponse): List<String> {
        val cardsResult = ArrayList<String>()

        for (card in apiResponse.cards) {
            if (!cardsResult.contains(card.name)) {
                cardsResult.add(card.name)
            }
        }

        if (cardsResult.isEmpty()) {
            cardsResult.add("No results found")
        }

        return cardsResult
    }
}