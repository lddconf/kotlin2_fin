package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.model.api.IDataSource
import com.example.foodviewer.mvp.model.api.IIngredientImageUrlSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Named("baseUrl")
    @Provides
    fun baseUrl(): String = "https://www.thecocktaildb.com/api/json/v1/1/"

    @Singleton
    @Named("ingredientsImageBaseUrl")
    @Provides
    fun baseIngredientImagesUrl() : String = "https://www.thecocktaildb.com/images/ingredients/"


    @Singleton
    @Named("gsonApi")
    @Provides
    fun gsonApi() : Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun api(@Named("baseUrl") baseUrl: String, @Named("gsonApi") gson: Gson) : IDataSource = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(IDataSource::class.java)

    @Singleton
    @Provides
    fun apiTemplateHolder(@Named("ingredientsImageBaseUrl") baseUrl: String) : IIngredientImageUrlSource = object : IIngredientImageUrlSource {
        private val baseUrl= "https://www.thecocktaildb.com/images/ingredients/"

        override fun ingredientSmallImageURLByName(name: String): String {
            return String.format(Locale.getDefault(), "$baseUrl%s-Small.png", name)
        }

        override fun ingredientMediumImageURLByName(name: String): String {
            return String.format(Locale.getDefault(), "$baseUrl%s-Medium.png", name)
        }
    }
}