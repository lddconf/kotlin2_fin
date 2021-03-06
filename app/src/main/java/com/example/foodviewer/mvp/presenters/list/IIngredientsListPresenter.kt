package com.example.foodviewer.mvp.presenters.list

import com.example.foodviewer.ui.adapter.IngredientsInBarRVAdapterCheckableInBar

interface IIngredientsListPresenter {
    var itemClickListener: ((IIngredientsListItemView) -> Unit)?
    var itemInBarCheckedListener: ((Int, Boolean) -> Unit)?

    fun bindView(view: IngredientsInBarRVAdapterCheckableInBar.IngredientsInBarViewHolder)
    fun getCount(): Int
}