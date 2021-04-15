package com.example.foodviewer.ui

import android.app.Application
import com.example.foodviewer.di.AppComponent
import com.example.foodviewer.di.DaggerAppComponent
import com.example.foodviewer.di.module.AppModule

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var appComponent: AppComponent

    /*
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder
        get() = cicerone.getNavigatorHolder()

    val router
        get() = cicerone.router
*/

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    }
}