package com.example.foodviewer.ui.adapter.tab

import androidx.fragment.app.Fragment

typealias TabFragmentCreator = () -> Fragment

class TabFragmentHolder(
    val tabName : String?,
    private val fragmentCreator: TabFragmentCreator
) {
    private var fragment : Fragment? = null

    fun createInstance() : Fragment? {
        fragment = fragmentCreator.invoke()
        return fragment
    }

    fun getInstance() : Fragment? {
        return fragment
    }
}