package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.view.ISplashActivityView
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable.interval
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class SplashActivityPresenter() : MvpPresenter<ISplashActivityView>() {
    @Inject
    lateinit var cocktailApi: ICocktailDetails
    @Inject
    lateinit var ingredientsApi: IIngredientDetails

    @Inject
    @field:Named("UIThread")
    lateinit var uiSchelduer: Scheduler

    companion object {
        const val SPLASH_ACTIVITY_TIMER_MS = 1500.toLong()
    }

    private val timer : Completable by lazy {
        Completable.timer(SPLASH_ACTIVITY_TIMER_MS, TimeUnit.MILLISECONDS, Schedulers.io())
    }

    private var disposable : Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        disposable = timer.observeOn(uiSchelduer).subscribe {
            updateCompleted()
        }
    }

    private fun updateCompleted() {
        viewState.startMainActivityAndClose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}