package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.model.entity.bar.IBarProperties
import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.model.entity.bar.RoomBarProperties
import com.example.foodviewer.mvp.model.entity.bar.RoomFavoriteCocktails
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.ui.navigation.AndroidAppScreens
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MyCocktailsModule {
    @Singleton
    @Provides
    fun barProperties(db : Database): IBarProperties =  RoomBarProperties(db)

    @Singleton
    @Provides
    fun favoriteCocktails(db : Database): IFavoriteCocktails = RoomFavoriteCocktails(db)
}