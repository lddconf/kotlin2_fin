package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.entity.cache.*
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

    @Singleton
    @Provides
    fun cacheInvalidator(api: IDataSource, cocktailsCache: ICocktailsCache, ingredientsCache : IIngredientsCache) : ICacheInvalidator = CacheInvalidator(api, cocktailsCache, ingredientsCache)
}