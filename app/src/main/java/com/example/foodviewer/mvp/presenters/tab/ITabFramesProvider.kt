package com.example.foodviewer.mvp.presenters.tab
import com.example.foodviewer.ui.adapter.tab.TabFragmentHolder

interface ITabFramesProvider {
    fun fragmentFactory(position: Int) : TabFragmentHolder?
    fun itemCount(): Int
}