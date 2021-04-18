package com.example.foodviewer.ui.adapter.tab

import androidx.fragment.app.Fragment

typealias TabFragmentCreator = () -> Fragment

data class TabFragmentFactory(
    val tabName : String?,
    val fragmentCreator: TabFragmentCreator
)