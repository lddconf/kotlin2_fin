package com.example.foodviewer.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodviewer.R
import com.example.foodviewer.databinding.ActivityMainBinding
import com.example.foodviewer.mvp.presenters.MainPresenter
import com.example.foodviewer.mvp.view.IMainActivityView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.example.foodviewer.ui.navigation.AndroidAppScreens
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), IMainActivityView {

    private val navigator = AppNavigator(this, R.id.container)

    private var vb: ActivityMainBinding? = null

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router, AndroidAppScreens())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is OnBackClickListener && it.onBackClicked()) {
                return
            }
        }
        presenter.backClicked()
    }

    override fun onDestroy() {
        super.onDestroy()
        vb = null
    }
}