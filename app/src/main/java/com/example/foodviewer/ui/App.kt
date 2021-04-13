package com.example.foodviewer.ui

import android.app.Application
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder
        get() = cicerone.getNavigatorHolder()

    val router
        get() = cicerone.router


    override fun onCreate() {
        super.onCreate()
        instance = this
        Database.create(this)
    }
}