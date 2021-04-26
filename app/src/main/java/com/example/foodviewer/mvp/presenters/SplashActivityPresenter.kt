package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.cache.CacheInvalidator
import com.example.foodviewer.mvp.model.entity.cache.ICacheInvalidator
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.view.ISplashActivityView
import com.github.terrakok.cicerone.Router
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

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    @Inject
    lateinit var cacheInvalidator: ICacheInvalidator

    companion object {
        const val SPLASH_ACTIVITY_TIMER_MS = 1500.toLong()
    }

    private val timer : Completable by lazy {
        Completable.timer(SPLASH_ACTIVITY_TIMER_MS, TimeUnit.MILLISECONDS, Schedulers.io())
    }

    private var disposable : Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        //val ingredientsObservable = cacheInvalidator.invalidateCacheIngredients()
        val cocktailsObservable = cacheInvalidator.invalidateCacheCocktailsIngredients()

        //val allObservable = ingredientsObservable.mergeWith(cocktailsObservable)

        disposable = cocktailsObservable.observeOn(uiSchelduer).subscribe({
            updateCompleted()
        }, { error->
            viewState.displayError(error.localizedMessage ?: "")
            updateCompleted()
        })


        /*
        disposable = timer.observeOn(uiSchelduer).subscribe {
            updateCompleted()
        }
        */
        //Update all ingredients database
    }

    private fun updateCompleted() {
        router.replaceScreen(screens.mainWindow())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}