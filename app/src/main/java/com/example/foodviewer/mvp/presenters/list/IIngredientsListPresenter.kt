package com.example.foodviewer.mvp.presenters.list

import com.example.foodviewer.ui.adapter.IngredientsInBarRVAdapter

interface IIngredientsListPresenter {
    var itemClickListener: ((IIngredientsListItemView) -> Unit)?
    var itemInBarCheckedListener: ((Int, Boolean) -> Unit)?

    fun bindView(view: IngredientsInBarRVAdapter.ViewHolder)
    fun getCount(): Int
}