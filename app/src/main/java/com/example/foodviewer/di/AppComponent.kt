package com.example.foodviewer.di

import com.example.foodviewer.di.module.*
import com.example.foodviewer.mvp.presenters.*
import com.example.foodviewer.ui.activity.MainActivity
import com.example.foodviewer.ui.activity.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CiceroneModule::class,
        DatabaseModule::class,
        ImageLoaderModule::class,
        JavaRxSchelduesModule::class,
        MyCocktailsModule::class,
        CocktailDetailsModule::class,
        CacheModule::class
    ]
)
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(cocktailDetailsPresenter: CocktailDetailsPresenter)
    fun inject(ingredientDetailsPresenter: IngredientDetailsPresenter)
    fun inject(ingredientsAmountPresenter: CocktailDetailsPresenter.IngredientsAmountPresenter)
    //fun inject(cocktailWithIngredientPresenter: IngredientDetailsPresenter.CocktailWithIngredientPresenter)
    fun inject(splashActivityPresenter: SplashActivityPresenter)

    fun inject(cocktailsWithIngredientsPresenter: CocktailsWithIngredientsPresenter)
    fun inject(cocktailWithIngredientPresenter: CocktailsWithIngredientsPresenter.CocktailWithIngredientPresenter)

    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)
}