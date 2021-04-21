package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.bar.IBarProperties
import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.tab.ICocktailListChangeable
import com.example.foodviewer.mvp.presenters.tab.ITabFramesProvider
import com.example.foodviewer.mvp.tabgroups.ICocktailsTabGroup
import com.example.foodviewer.mvp.tabgroups.IIngredientsTabGroup
import com.example.foodviewer.mvp.view.IIngredientsDetailsTabView
import com.example.foodviewer.ui.adapter.tab.TabFragmentHolder
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

class IngredientsDetailsTabPresenter() : MvpPresenter<IIngredientsDetailsTabView>() {
    @Inject
    lateinit var cocktailApi: ICocktailDetails

    @Inject
    @field:Named("UIThread")
    lateinit var uiSchelduer: Scheduler

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IAppScreens

    @Inject
    lateinit var barProperties: IBarProperties

    @Inject
    lateinit var ingredientsTabGroup: IIngredientsTabGroup

    private var compositeDisposable = CompositeDisposable()

    val tabsViewPresenter by lazy {
        IngredientsTabViewProvider(ingredientsTabGroup)
    }

    class IngredientsTabViewProvider(val ingredientsTabGroup: IIngredientsTabGroup) : ITabFramesProvider {
        private enum class TABS(val id: Int) {
            ALL_INGREDIENTS(0),
            INGREDIENTS_IN_BAR(1)
        }

        private val fragmentHolders = arrayListOf<TabFragmentHolder>(
                ingredientsTabGroup.allIngredients(),
                ingredientsTabGroup.ingredientsInBar()
        )

        override fun fragmentFactory(position: Int) = when (position) {
            TABS.ALL_INGREDIENTS.id -> {
                fragmentHolders[TABS.ALL_INGREDIENTS.id]
            }
            TABS.INGREDIENTS_IN_BAR.id -> {
                fragmentHolders[TABS.INGREDIENTS_IN_BAR.id]
            }
            else -> null
        }
        override fun itemCount() = fragmentHolders.size
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initTabs()
        viewState.initAppBar()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}