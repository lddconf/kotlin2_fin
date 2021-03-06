package com.example.foodviewer.di.module

import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.tabgroups.ICocktailsTabGroup
import com.example.foodviewer.mvp.tabgroups.IIngredientsTabGroup
import com.example.foodviewer.ui.navigation.AndroidAppScreens
import com.example.foodviewer.ui.tabgroups.CocktailsDetailsTabGroup
import com.example.foodviewer.ui.tabgroups.IngredientsDetailsTabGroup
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CocktailsTabsGroup {
    @Singleton
    @Provides
    fun cocktailsTabScreens(): ICocktailsTabGroup = CocktailsDetailsTabGroup()

    @Singleton
    @Provides
    fun ingredientsTabScreens(): IIngredientsTabGroup = IngredientsDetailsTabGroup()
}