package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.model.entity.cache.ICocktailsCache
import com.example.foodviewer.mvp.model.entity.cache.IIngredientsCache
import com.example.foodviewer.mvp.model.entity.cache.RoomCocktailsCache
import com.example.foodviewer.mvp.model.entity.cache.RoomIngredientsCache
import com.example.foodviewer.mvp.model.entity.room.db.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun ingredientsCache(db: Database) : IIngredientsCache =  RoomIngredientsCache(db)

    @Singleton
    @Provides
    fun cocktailsCache(db: Database) : ICocktailsCache = RoomCocktailsCache(db)
}