package com.example.foodviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}