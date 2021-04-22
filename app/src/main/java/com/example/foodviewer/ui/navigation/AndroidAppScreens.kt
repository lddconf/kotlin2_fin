package com.example.foodviewer.ui.navigation

import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.ui.activity.MainActivity
import com.example.foodviewer.ui.activity.SplashActivity
import com.example.foodviewer.ui.fragment.*
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidAppScreens : IAppScreens {
    override fun cocktailDetails(cocktailID: Long?): Screen =
            FragmentScreen {
                CocktailDetailsFragment.newInstance(cocktailID)
            }

    override fun ingredientDetails(ingredientName: String?): Screen = FragmentScreen {
        IngredientDetailsFragment.newInstance(ingredientName)
    }

    override fun cocktailsProperties(): Screen = FragmentScreen {
        CocktailsDetailsTabFragment.newInstance()
    }

    override fun splashWindow(): Screen = ActivityScreen {
        SplashActivity.getIntent(it)
    }

    override fun mainWindow(): Screen = ActivityScreen {
        MainActivity.getIntent(it)
    }

    override fun ingredients(): Screen = FragmentScreen {
        IngredientsDetailsTabFragment.newInstance()
    }

    override fun aboutWindow(): Screen = FragmentScreen {
        AppAboutFragment.newInstance()
    }

    override fun ingredientsInBar(): Screen = FragmentScreen {
        IngredientsListFragment.newInstance()
    }
}