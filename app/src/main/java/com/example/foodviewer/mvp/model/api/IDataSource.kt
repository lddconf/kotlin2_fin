package com.example.foodviewer.mvp.model.api

import com.example.foodviewer.mvp.model.entity.json.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {
    @GET("search.php")
    fun searchCocktailByName(@Query("s") name: String): Single<CocktailsDetailsJSON>

    @GET("search.php")
    fun listCocktailByLetter(@Query("f") letter: String): Single<CocktailsDetailsJSON>

    @GET("search.php")
    fun searchIngredientByName(@Query("i") name: String): Single<IngredientsDescription>

    fun searchCocktailById(@Query("i") id: Long): Single<CocktailsDetailsJSON>

    @GET("search.php")
    fun searchIngredientById(@Query("iid") id: Long): Single<IngredientsDescription>

    @GET("random.php")
    fun getRandomCocktail(): Single<CocktailsDetailsJSON>

    @GET("filter.php")
    fun searchCocktailByIngredient(@Query("i") name: String): Single<Cocktails>

    @GET("filter.php?a=Alcoholic")
    fun filterByAlcoholicType(@Query("a") type: String): Single<Cocktails>

    @GET("filter.php")
    fun filterByCategory(@Query("c") category: String): Single<Cocktails>

    @GET("filter.php")
    fun filterByGlass(@Query("g") glass: String): Single<Cocktails>

    @GET("list.php?c=list")
    fun getCategories(): Single<CategoryTypes>

    @GET("list.php?g=list")
    fun getGlasses(): Single<GlassTypes>

    @GET("list.php?i=list")
    fun getIngredients(): Single<IngredientTypes>

    @GET("list.php?a=list")
    fun getAlcoholic(): Single<AlcoholicTypes>
}