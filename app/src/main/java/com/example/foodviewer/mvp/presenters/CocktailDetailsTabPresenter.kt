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
        private enum class TABS(val id: Int) {
            MY_COCKTAILS(2),
            ALL_COCKTAILS(0),
            FAVORITE_COCKTAILS(1)
        }

        private val favoriteCocktails: MutableList<Cocktail> = mutableListOf()
        private val allCocktails: MutableList<Cocktail> = mutableListOf()
        private val myCocktails: MutableList<Cocktail> = mutableListOf()

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

        override fun itemCount() = fragmentHolders.size

        private fun updateFavoriteList() {
            fragmentHolders[TABS.FAVORITE_COCKTAILS.id].getInstance()?.let {
                it as ICocktailListChangeable
                it.cocktailList(favoriteCocktails)
            }
        }

        fun setFavoriteList(cocktailIds: List<Long>) {
            favoriteCocktails.clear()
            favoriteCocktails.addAll(allCocktails.filter { cocktailIds.contains(it.idDrink) })
            updateFavoriteList()
        }

        fun updateFavoriteList(id: Long, favorite: Boolean) {
            if (favorite) {
                if (favoriteCocktails.find { it.idDrink == id } == null) {
                    val cocktail = allCocktails.find { it.idDrink == id }
                    cocktail?.let {
                        favoriteCocktails.add(cocktail)
                        updateFavoriteList()
                    }
                }
            } else {
                favoriteCocktails.find { it.idDrink == id }?.let {
                    favoriteCocktails.remove(it)
                    updateFavoriteList()
                }
            }
        }

        fun setAllCocktailsList(cocktails: List<Cocktail>) {
            allCocktails.clear()
            allCocktails.addAll(cocktails)
            fragmentHolders[TABS.ALL_COCKTAILS.id].getInstance()?.let {
                it as ICocktailListChangeable
                it.cocktailList(allCocktails)
            }
        }

        fun setMyCocktailsList(cocktails: List<Cocktail>) {
            myCocktails.clear()
            myCocktails.addAll(cocktails)
            fragmentHolders[TABS.ALL_COCKTAILS.id].getInstance()?.let {
                it as ICocktailListChangeable
                it.cocktailList(myCocktails)
            }
        }

    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initTabs()
        loadAllCocktailsList()
        val subscribe = favoriteCocktails.favoriteCocktailChangedById().observeOn(uiSchelduer)
            .subscribe { favoriteState ->
                tabsViewPresenter.updateFavoriteList(favoriteState.id, favoriteState.favorite)
            }
        compositeDisposable.add(subscribe)
    }

    fun backClick(): Boolean {
        router.exit()
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun formFavoriteCocktails() {
        favoriteCocktails.allFavoriteCocktailIDs().observeOn(uiSchelduer).subscribe { ids ->
            tabsViewPresenter.setFavoriteList(ids)
        }
    }

    private fun loadAllCocktailsList() {
        val subscribe = cocktailApi.getAllCocktails().observeOn(uiSchelduer).subscribe(
            { allCocktails ->
                //Handle cocktails
                //val sotedAllCocktails = allCocktails.sortedBy { it.strDrink }
                tabsViewPresenter.setAllCocktailsList(allCocktails)
                formFavoriteCocktails()
            }, { error ->
                viewState.displayError(error.message ?: "Internal error")
            })
        compositeDisposable.add(subscribe)
    }
}