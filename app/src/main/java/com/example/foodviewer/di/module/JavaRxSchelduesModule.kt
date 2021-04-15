package com.example.foodviewer.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Named
import javax.inject.Singleton

@Module
class JavaRxSchelduesModule {

    @Singleton
    @Provides
    @Named("UIThread")
    fun uiSchelduer() : Scheduler  = AndroidSchedulers.mainThread()
}