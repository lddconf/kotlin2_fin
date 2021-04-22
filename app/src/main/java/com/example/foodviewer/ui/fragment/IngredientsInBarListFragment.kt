package com.example.foodviewer.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodviewer.mvp.presenters.IngredientsListPresenter
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.adapter.IngredientsInBarRVAdapterUncheckableInBar

class IngredientsInBarListFragment() : IngredientsListFragment() {
    override fun createPresenter(): IngredientsListPresenter {
        return IngredientsListPresenter().apply { App.instance.appComponent.inject(this) }
    }

    override fun initIngredientsList() {
        ingredientsListBinding?.ingredientsList?.layoutManager =
                LinearLayoutManager(requireContext())
        adapter = IngredientsInBarRVAdapterUncheckableInBar(presenter.ingredientDetailsPresenter, imageLoader)
        ingredientsListBinding?.ingredientsList?.adapter = adapter
    }

    companion object {
        private const val COCKTAIL_DETAILS_KEY = "IngredientsInBarListFragment.IngredientsInBarListFragment"

        @JvmStatic
        fun newInstance() = IngredientsInBarListFragment()
    }
}