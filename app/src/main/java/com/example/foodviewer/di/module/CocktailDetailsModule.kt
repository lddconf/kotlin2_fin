package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.api.IIngredientImageUrlSource
import com.example.foodviewer.mvp.model.entity.cache.ICocktailsCache
import com.example.foodviewer.mvp.model.entity.cache.IIngredientsCache
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.mvp.model.entity.room.requests.RoomCocktailDetailsFromCache
import com.example.foodviewer.mvp.model.entity.room.requests.RoomIngredientsDetailsFromCache
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.model.requests.RetrofitCocktailDetails
import com.example.foodviewer.mvp.model.requests.RetrofitIngredientDetails
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class CocktailDetailsModule {

    @Singleton
    @Provides
    @Named("NetworkCocktailsLoader")
    fun cocktailDetails(api: IDataSource, cache: ICocktailsCache): ICocktailDetails =
            RetrofitCocktailDetails(api, cache)

    @Singleton
    @Provides
    @Named("NetworkIngredientsLoader")
    fun ingredientDetails(
            api: IDataSource,
            ingredientsCache: IIngredientsCache,
            imageUrlProvider: IIngredientImageUrlSource
    ): IIngredientDetails = RetrofitIngredientDetails(api, ingredientsCache, imageUrlProvider)

    @Singleton
    @Provides
    fun cachedIngredientDetails(
            db: Database,
            imageUrlProvider: IIngredientImageUrlSource
    ): IIngredientDetails = RoomIngredientsDetailsFromCache(db, imageUrlProvider)

    @Singleton
    @Provides
    fun cachedCoctailDetails(
            db: Database,
            cocktailsCache: ICocktailsCache): ICocktailDetails = RoomCocktailDetailsFromCache(db, cocktailsCache)


}