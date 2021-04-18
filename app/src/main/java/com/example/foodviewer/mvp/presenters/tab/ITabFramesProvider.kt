package com.example.foodviewer.mvp.presenters.tab
import com.example.foodviewer.ui.adapter.tab.TabFragmentFactory

interface ITabFramesProvider {
    fun fragmentFactory(position: Int) : TabFragmentFactory?
    fun itemCount(): Int
}