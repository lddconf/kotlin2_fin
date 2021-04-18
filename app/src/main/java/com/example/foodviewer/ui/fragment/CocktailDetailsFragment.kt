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
import com.example.foodviewer.databinding.FragmentCoctailDetailsBinding
import com.example.foodviewer.mvp.presenters.CocktailDetailsPresenter
import com.example.foodviewer.mvp.view.ICocktailDetailsView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.IngredientsAmountRVAdapter
import com.example.foodviewer.ui.image.GlideImageLoader
import com.example.foodviewer.ui.listeners.OnBackClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader
import javax.inject.Inject

class CocktailDetailsFragment() : MvpAppCompatFragment(), ICocktailDetailsView,
        OnBackClickListener {
    private var cocktailDetailsBinding: FragmentCoctailDetailsBinding? = null
    private var adapter: IngredientsAmountRVAdapter? = null

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>


    private val presenter by moxyPresenter {
        var cocktailID: Long? = null
        arguments?.let {
            cocktailID = it.getLong(COCKTAIL_DETAILS_KEY)
        }
        CocktailDetailsPresenter(cocktailID).apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = FragmentCoctailDetailsBinding.inflate(inflater, container, false).also {
        cocktailDetailsBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        cocktailDetailsBinding?.cocktailRecipe?.setOnClickListener {
            presenter.recipeViewClicked()
        }

        cocktailDetailsBinding?.cocktailFavorite?.setOnClickListener {
            it as ToggleButton
            presenter.favoriteChanged(it.isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cocktailDetailsBinding = null
    }

    companion object {
        private const val COCKTAIL_DETAILS_KEY = "CocktailDetailsFragment.CocktailDetails"

        @JvmStatic
        fun newInstance(cocktailID: Long?) =
                CocktailDetailsFragment().apply {
                    arguments = Bundle().apply {
                        cocktailID?.let {
                            putLong(COCKTAIL_DETAILS_KEY, cocktailID)
                        }
                    }
                }
    }

    override fun onBackClicked(): Boolean = presenter.backClick()

    override fun collapseRecipeText(collapse: Boolean) {
        if (collapse) {
            cocktailDetailsBinding?.cocktailRecipe?.maxLines =
                    requireContext().resources.getInteger(R.integer.recipeCollapsedMaxLines)
            return
        }
        cocktailDetailsBinding?.cocktailRecipe?.maxLines = Int.MAX_VALUE
    }

    override fun cocktailName(name: String) {
        cocktailDetailsBinding?.cocktailName?.text = name
    }

    override fun favoriteState(state: Boolean) {
        cocktailDetailsBinding?.cocktailFavorite?.isChecked = state
    }

    override fun recipeText(text: String) {
        cocktailDetailsBinding?.cocktailRecipe?.text = text
    }

    override fun loadCocktailThumb(url: String) {
        cocktailDetailsBinding?.apply {
            imageLoader.load(url, cocktailThumb)
        }
    }

    override fun initIngredients() {
        cocktailDetailsBinding?.cocktailIngredients?.layoutManager =
                LinearLayoutManager(requireContext())
        adapter = IngredientsAmountRVAdapter(presenter.ingredientAmountPresenter, imageLoader)
        cocktailDetailsBinding?.cocktailIngredients?.adapter = adapter
    }

    override fun updateIngredientList() {
        cocktailDetailsBinding?.cocktailIngredients?.adapter?.notifyDataSetChanged()
    }

    override fun notifyIngredientInBarChanged(index: Int) {
        cocktailDetailsBinding?.cocktailIngredients?.adapter?.notifyItemChanged(index)
    }

    override fun displayError(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show()
    }

    override fun showCocktailAddedToFavoriteNotification() {
        Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.cocktail_added_to_favorites),
                Toast.LENGTH_SHORT
        ).show()
    }

    override fun showCocktailRemovedFromFavoriteNotification() {
        Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.cocktail_removed_from_favorites),
                Toast.LENGTH_SHORT
        ).show()
    }
}