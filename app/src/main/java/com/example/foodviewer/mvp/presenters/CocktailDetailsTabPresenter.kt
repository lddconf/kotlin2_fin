package com.example.foodviewer.mvp.presenters

import com.example.foodviewer.mvp.model.entity.bar.IFavoriteCocktails
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.model.requests.ICocktailDetails
import com.example.foodviewer.mvp.navigation.IAppScreens
import com.example.foodviewer.mvp.presenters.tab.ICocktailListChangeable
import com.example.foodviewer.mvp.presenters.tab.ITabFramesProvider
import com.example.foodviewer.mvp.tabgroups.ICocktailsTabGroup
import com.example.foodviewer.mvp.view.ICocktailsDetailsTabView
import com.example.foodviewer.ui.adapter.tab.TabFragmentHolder
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
            set(cocktails: List<Cocktail>) {
                field = cocktails
                fragmentHolders[TABS.FAVORITE_COCKTAILS.id].getInstance()?.let {
                    if (it is ICocktailListChangeable) it.cocktailList(cocktails)
                }
            }

        var allCocktails: List<Cocktail> = listOf()
            set(cocktails: List<Cocktail>) {
                field = cocktails
                fragmentHolders[TABS.ALL_COCKTAILS.id].getInstance()?.let {
                    if (it is ICocktailListChangeable) it.cocktailList(cocktails)
                }
            }

        var myCocktails: List<Cocktail> = listOf()
            set(cocktails: List<Cocktail>) {
                field = cocktails
                fragmentHolders[TABS.MY_COCKTAILS.id].getInstance()?.let {
                    if (it is ICocktailListChangeable) it.cocktailList(cocktails)
                }
            }

        private val fragmentHolders = arrayListOf<TabFragmentHolder>(
                cocktailTabGroup.allCocktails(allCocktails),
                cocktailTabGroup.favoriteCocktails(favoriteCocktails)
        )

        override fun fragmentFactory(position: Int) = when (position) {
            TABS.ALL_COCKTAILS.id -> {
                fragmentHolders[TABS.ALL_COCKTAILS.id]
            }
            TABS.FAVORITE_COCKTAILS.id -> {
                fragmentHolders[TABS.FAVORITE_COCKTAILS.id]
            }
            TABS.MY_COCKTAILS.id -> {
                fragmentHolders[TABS.MY_COCKTAILS.id]
            }
            else -> null
        }

        override fun itemCount(): Int {
            return 2
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initTabs()
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
        favoriteCocktails.allFavoriteCocktailIDs().observeOn(uiSchelduer).subscribe { ids ->
            tabsViewPresenter.favoriteCocktails = cocktails.filter { ids.contains(it.idDrink) }
        }
    }

    private fun loadAllCocktailsList() {
        cocktailApi.getAllCocktails().observeOn(uiSchelduer).subscribe(
                { allCocktails ->
                    //Handle cocktails
                    tabsViewPresenter.allCocktails = allCocktails.sortedBy { it.strDrink }
                    formFavoriteCocktails(tabsViewPresenter.allCocktails)
                }, { error ->
            viewState.displayError(error.message ?: "Internal error")
        })
    }


}