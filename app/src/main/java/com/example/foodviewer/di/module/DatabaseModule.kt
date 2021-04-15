package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(app: App): Database = Database.let {
        Database.create(app)
        Database.getInstance()
    }
}