package com.example.foodviewer.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodviewer.R
import com.example.foodviewer.databinding.ActivitySplashBinding
import com.example.foodviewer.mvp.presenters.SplashActivityPresenter
import com.example.foodviewer.mvp.view.ISplashActivityView
import com.example.foodviewer.ui.App
import com.github.terrakok.cicerone.NavigatorHolder
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class SplashActivity : MvpAppCompatActivity(), ISplashActivityView {
    private var vb: ActivitySplashBinding? = null

    companion object {
        fun getIntent(context: Context) {
            Intent(context, SplashActivity::class.java)
        }
    }

    private val presenter by moxyPresenter {
        SplashActivityPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        vb = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }

    override fun startMainActivityAndClose() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        vb = null
    }
}
