package com.example.foodviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodviewer.R
import com.example.foodviewer.databinding.FragmentCocktailsListWithIngredientsBinding
import com.example.foodviewer.databinding.FragmentIngredientDetailsBinding
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.presenters.CocktailsWithIngredientsPresenter
import com.example.foodviewer.mvp.presenters.IngredientDetailsPresenter
import com.example.foodviewer.mvp.presenters.tab.ICocktailListChangeable
import com.example.foodviewer.mvp.view.ICocktailWithIngredientsView
import com.example.foodviewer.mvp.view.IIngredientDetailsView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.CocktailWithIngredientRVAdapter
import com.example.foodviewer.ui.image.GlideImageLoader
import com.example.foodviewer.ui.listeners.OnBackClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader

class CocktailsListWithIngredientsFragment() : MvpAppCompatFragment(), ICocktailWithIngredientsView,
        OnBackClickListener, ICocktailListChangeable {
    private var vb: FragmentCocktailsListWithIngredientsBinding? = null
    private var adapter: CocktailWithIngredientRVAdapter? = null

    private val imageLoader: IImageLoader<ImageView> by lazy {
        GlideImageLoader(true)
    }

    private val presenter by moxyPresenter {
        var cocktails: List<Cocktail>? = null
        arguments?.let {
            cocktails = it.getParcelableArrayList(COCKTAIL_WITH_INGREDIENTS_KEY)
        }
        CocktailsWithIngredientsPresenter(cocktails).apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = FragmentCocktailsListWithIngredientsBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    companion object {
        private const val COCKTAIL_WITH_INGREDIENTS_KEY = "CocktailsListWithIngredientsFragment.cocktails"

        @JvmStatic
        fun newInstance(cocktails: List<Cocktail>?) =
                CocktailsListWithIngredientsFragment().apply {
                    arguments = Bundle().apply {
                        cocktails?.let {
                            putParcelableArrayList(COCKTAIL_WITH_INGREDIENTS_KEY, ArrayList(it))
                        }
                    }
                }
    }

    override fun onBackClicked(): Boolean = presenter.backClick()

    override fun initCocktailsWith() {
        vb?.cocktailsWithIngredient?.layoutManager =
                LinearLayoutManager(requireContext())
        adapter = CocktailWithIngredientRVAdapter(presenter.cocktailWithPresenter, imageLoader)
        vb?.cocktailsWithIngredient?.adapter = adapter
    }

    override fun updateCocktailsWithList() {
        vb?.cocktailsWithIngredient?.adapter?.notifyDataSetChanged()
    }

    override fun displayError(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_LONG).show()
    }

    override fun cocktailList(cocktailList: List<Cocktail>) {
       presenter.cocktailList(cocktailList)
    }

    override fun cocktailList(): List<Cocktail>? = presenter.cocktailList()
}