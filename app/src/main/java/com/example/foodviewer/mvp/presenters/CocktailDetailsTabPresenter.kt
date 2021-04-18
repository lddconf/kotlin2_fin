package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.tab.ITabFramesProvider
import com.example.foodviewer.mvp.tabgroups.ICocktailsTabGroup
import com.example.foodviewer.mvp.view.ICocktailsDetailsTabView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

class CocktailDetailsTabPresenter() : MvpPresenter<ICocktailsDetailsTabView>() {
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
    lateinit var favoriteCocktails: IFavoriteCocktails

    @Inject
    lateinit var cocktailTabGroup: ICocktailsTabGroup

    private var compositeDisposable = CompositeDisposable()

    val tabsViewPresenter by lazy {
        CocktailsTabViewProvider(cocktailTabGroup)
    }

    class CocktailsTabViewProvider(val cocktailTabGroup: ICocktailsTabGroup) : ITabFramesProvider {
        enum class TABS(val id: Int) {
            MY_COCKTAILS(2),
            ALL_COCKTAILS(0),
            FAVORITE_COCKTAILS(1)
        }

        var favoriteCocktails: List<Cocktail> = listOf()
        var allCocktails: List<Cocktail> = listOf()
        var myCocktails: List<Cocktail> = listOf()

        override fun fragmentFactory(position: Int) = when (position) {
            TABS.ALL_COCKTAILS.id -> cocktailTabGroup.allCocktails(allCocktails)
            TABS.FAVORITE_COCKTAILS.id -> cocktailTabGroup.favoriteCocktails(favoriteCocktails)
            TABS.MY_COCKTAILS.id -> cocktailTabGroup.myCocktails(myCocktails)
            else -> null
        }

        override fun itemCount(): Int {
            return 2
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadAllCocktailsList()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun formFavoriteCocktails(cocktails: List<Cocktail>) {
        favoriteCocktails.allFavoriteCocktailIDs().observeOn(uiSchelduer).subscribe { ids->
            tabsViewPresenter.favoriteCocktails = cocktails.filter { ids.contains(it.idDrink) }
        }
    }

    private fun loadAllCocktailsList() {
        cocktailApi.getAllCocktails().observeOn(uiSchelduer).subscribe(
            { allCocktails ->
                //Handle cocktails
                tabsViewPresenter.allCocktails = allCocktails.sortedBy { it.strDrink }
                formFavoriteCocktails(tabsViewPresenter.allCocktails)
                viewState.initTabs()

                //favoriteCocktails.
                //viewState.updateTabs()//(CocktailsTabViewProvider.TABS.ALL_COCKTAILS.id)
            }, { error ->
                viewState.displayError(error.message ?: "Internal error")
            })

    }


}