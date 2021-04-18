package com.example.foodviewer.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodviewer.mvp.presenters.tab.ITabFramesProvider


class CocktailsTabSPAdapter(val fragment: Fragment, val tabsViewPresenter: ITabFramesProvider) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabsViewPresenter.itemCount()
    }

    override fun createFragment(position: Int): Fragment {
        return tabsViewPresenter.fragmentFactory(position)?.fragmentCreator?.invoke() ?: Fragment()
    }
}