package com.example.foodviewer.mvp.presenters.list

import com.example.foodviewer.ui.adapter.IngredientsAmountRVAdapter

interface IIngredientsAmountListPresenter {
    var itemClickListener: ((IIngredientsAmountItemView) -> Unit)?
    fun bindView(view: IngredientsAmountRVAdapter.ViewHolder)
    fun getCount(): Int
}