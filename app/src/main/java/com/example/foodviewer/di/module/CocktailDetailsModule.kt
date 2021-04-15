package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.model.api.ApiHolder
import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.api.IIngredientImageUrlSource
import com.example.foodviewer.mvp.model.entity.cache.ICocktailsCache
import com.example.foodviewer.mvp.model.entity.cache.IIngredientsCache
import com.example.foodviewer.mvp.model.entity.cache.RoomIngredientsCache
import com.example.foodviewer.mvp.model.entity.room.db.Database
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.model.requests.IIngredientDetails
import com.example.foodviewer.mvp.model.requests.RetrofitCocktailDetails
import com.example.foodviewer.mvp.model.requests.RetrofitIngredientDetails
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CocktailDetailsModule {

    @Singleton
    @Provides
    fun cocktailDetails(api: IDataSource, cache: ICocktailsCache): ICocktailDetails =
        RetrofitCocktailDetails(api, cache)

    @Singleton
    @Provides
    fun ingredientDetails(
        api: IDataSource,
        ingredientsCache: IIngredientsCache,
        imageUrlProvider: IIngredientImageUrlSource
    ): IIngredientDetails = RetrofitIngredientDetails(api, ingredientsCache, imageUrlProvider)
}