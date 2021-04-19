package com.example.foodviewer.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.foodviewer.mvp.model.entity.json.Cocktail
import com.example.foodviewer.mvp.presenters.tab.ICocktailListChangeable
import com.example.foodviewer.mvp.presenters.tab.ITabFramesProvider


class CocktailsTabSPAdapter(val fragment: Fragment, val tabsViewPresenter: ITabFramesProvider) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabsViewPresenter.itemCount()
    }

    override fun createFragment(position: Int) =
            tabsViewPresenter.fragmentFactory(position)?.createInstance() ?: Fragment()

}