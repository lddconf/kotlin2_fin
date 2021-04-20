package com.example.foodviewer.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodviewer.R
import com.example.foodviewer.databinding.ActivityMainBinding
import com.example.foodviewer.mvp.presenters.MainPresenter
import com.example.foodviewer.mvp.view.IMainActivityView
import com.example.foodviewer.ui.App
import com.example.foodviewer.ui.listeners.OnBackClickListener
import com.example.foodviewer.ui.navigation.AndroidAppScreens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), IMainActivityView {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private var vb: ActivityMainBinding? = null
    private val navigator = AppNavigator(this, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }

    override fun initAppBar() {
        setSupportActionBar(vb?.mainToolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is OnBackClickListener && it.onBackClicked()) {
                val fragmentsCount = supportFragmentManager.fragments.size
                if ( fragmentsCount <= 1) {
                    return@forEach
                } else return
            }
        }
        presenter.backClicked()
    }

    override fun onDestroy() {
        super.onDestroy()
        vb = null
    }
}