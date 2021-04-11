package com.example.foodviewer.ui.fragment

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import com.example.foodviewer.R
import com.example.foodviewer.databinding.FragmentCoctailDetailsBinding
import com.example.foodviewer.mvp.model.entity.CocktailDetails
import com.example.foodviewer.mvp.presenters.CocktailDetailsPresenter
import com.example.foodviewer.mvp.view.ICocktailDetailsView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.listeners.OnBackClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class CocktailDetailsFragment : MvpAppCompatFragment(), ICocktailDetailsView, OnBackClickListener {
    private var cocktailDetailsBinding: FragmentCoctailDetailsBinding? = null

    private val presenter by moxyPresenter {
        var cocktailDetails: CocktailDetails? = null
        arguments?.let {
            cocktailDetails = it.getParcelable(COCKTAIL_DETAILS_KEY)
        }
        CocktailDetailsPresenter(cocktailDetails, App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        fun newInstance(cocktailDetails: CocktailDetails?) =
            CocktailDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(COCKTAIL_DETAILS_KEY, cocktailDetails)
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


}