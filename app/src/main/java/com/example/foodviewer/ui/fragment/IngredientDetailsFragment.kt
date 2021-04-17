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
import com.example.foodviewer.databinding.FragmentIngredientDetailsBinding
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.presenters.IngredientDetailsPresenter
import com.example.foodviewer.mvp.view.IIngredientDetailsView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.CocktailWithIngredientRVAdapter
import com.example.foodviewer.ui.image.GlideImageLoader
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader

class IngredientDetailsFragment() : MvpAppCompatFragment(), IIngredientDetailsView,
    OnBackClickListener {
    private var ingredientDetailsBinding: FragmentIngredientDetailsBinding? = null
    private var adapter: CocktailWithIngredientRVAdapter? = null

    private val imageLoader: IImageLoader<ImageView> by lazy {
        GlideImageLoader(true)
    }

    private val presenter by moxyPresenter {
        var ingredientName: String? = null
        arguments?.let {
            ingredientName = it.getString(INGREDIENT_DETAILS_KEY)
        }
        IngredientDetailsPresenter(
            ingredientName).apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentIngredientDetailsBinding.inflate(inflater, container, false).also {
        ingredientDetailsBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientDetailsBinding?.ingredientDescription?.setOnClickListener {
            presenter.ingredientDescriptionViewClicked()
        }

        ingredientDetailsBinding?.ingredientInBar?.setOnClickListener {
            it as ToggleButton
            presenter.inBarState(it.isChecked)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        ingredientDetailsBinding = null
    }

    companion object {
        private const val INGREDIENT_DETAILS_KEY = "IngredientDetailsFragment.IngredientDetails"

        @JvmStatic
        fun newInstance(ingredient: String?) =
            IngredientDetailsFragment().apply {
                arguments = Bundle().apply {
                    ingredient?.let {
                        putString(INGREDIENT_DETAILS_KEY, it)
                    }
                }
            }
    }

    override fun onBackClicked(): Boolean = presenter.backClick()

    override fun collapseIngredientDescription(collapse: Boolean) {
        if (collapse) {
            ingredientDetailsBinding?.ingredientDescription?.maxLines =
                requireContext().resources.getInteger(R.integer.recipeCollapsedMaxLines)
            return
        }
        ingredientDetailsBinding?.ingredientDescription?.maxLines = Int.MAX_VALUE
    }

    override fun ingredientName(name: String) {
        ingredientDetailsBinding?.ingredientName?.text = name
    }

    override fun inBarState(state: Boolean) {
        ingredientDetailsBinding?.ingredientInBar?.isChecked = state
    }

    override fun ingredientDescription(text: String) {
        ingredientDetailsBinding?.ingredientDescription?.text = text
    }

    override fun loadIngredientThumb(url: String) {
        ingredientDetailsBinding?.apply {
            imageLoader.load(url, ingredientThumb)
        }
    }
/*
    override fun initCocktailsWith() {

        ingredientDetailsBinding?.cocktailsWithIngredient?.layoutManager =
            LinearLayoutManager(requireContext())
        adapter = CocktailWithIngredientRVAdapter(presenter.cocktailWithPresenter, imageLoader)
        ingredientDetailsBinding?.cocktailsWithIngredient?.adapter = adapter

    }
*/
    override fun updateCocktailsWithList(cocktails: List<Cocktail>) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.cocktails_with_container, CocktailsListWithIngredientsFragment.newInstance(cocktails))
        transaction.commit()
    }

    override fun displayError(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_LONG).show()
    }

    override fun showIngredientAddedNotification(ingredientName: String) {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.ingredient_added_to_bar) + "$ingredientName",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showIngredientRemovedNotification(ingredientName: String) {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.ingredient_removed_from__bar) + "$ingredientName",
            Toast.LENGTH_SHORT
        ).show()
    }
}