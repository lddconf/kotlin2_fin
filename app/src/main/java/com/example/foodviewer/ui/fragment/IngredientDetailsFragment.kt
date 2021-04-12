package com.example.foodviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import com.example.foodviewer.R
import com.example.foodviewer.databinding.FragmentIngredientDetailsBinding
import com.example.foodviewer.mvp.model.api.ApiHolder
import com.example.foodviewer.mvp.model.entity.json.IngredientDetails
import com.example.foodviewer.mvp.model.requests.RetrofitCocktailDetails
import com.example.foodviewer.mvp.model.requests.RetrofitIngredientDetails
import com.example.foodviewer.mvp.presenters.IngredientDetailsPresenter
import com.example.foodviewer.mvp.view.IIngredientDetailsView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.IngredientsAmountRVAdapter
import com.example.foodviewer.ui.image.GlideImageLoader
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.example.foodviewer.ui.navigation.AndroidAppScreens
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader

class IngredientDetailsFragment() : MvpAppCompatFragment(), IIngredientDetailsView,
    OnBackClickListener {
    private var ingredientDetailsBinding: FragmentIngredientDetailsBinding? = null
    private var adapter: IngredientsAmountRVAdapter? = null

    private val imageLoader: IImageLoader<ImageView> by lazy {
        GlideImageLoader()
    }

    private val presenter by moxyPresenter {
        var ingredientName: String? = null
        arguments?.let {
            ingredientName = it.getString(INGREDIENT_DETAILS_KEY)
        }
        IngredientDetailsPresenter(
            ingredientName,
            RetrofitCocktailDetails(ApiHolder.api),
            RetrofitIngredientDetails(ApiHolder.api, ApiHolder.apiTemplateHolder),
            App.instance.router,
            AndroidAppScreens(),
            AndroidSchedulers.mainThread()
        )
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

    override fun initCocktailsWith() {
        /*
        ingredientDetailsBinding?.cocktailsWithIndgredient?.layoutManager =
            LinearLayoutManager(requireContext())
        adapter = IngredientsAmountRVAdapter(presenter.ingredientAmountPresenter, imageLoader)
        ingredientDetailsBinding?.cocktailsWithIndgredient?.adapter = adapter
         */
    }

    override fun updateCocktailsWithList() {
        ingredientDetailsBinding?.cocktailsWithIngredient?.adapter?.notifyDataSetChanged()
    }

    override fun displayError(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show()
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