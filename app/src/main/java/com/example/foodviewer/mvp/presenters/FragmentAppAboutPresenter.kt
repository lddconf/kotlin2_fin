package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.view.IAppAboutView
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

class FragmentAppAboutPresenter() : MvpPresenter<IAppAboutView>() {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initAppBar()
    }

    fun backClick() : Boolean {
        router.exit()
        return true
    }
}