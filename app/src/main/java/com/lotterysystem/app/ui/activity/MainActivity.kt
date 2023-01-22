package com.lotterysystem.app.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.lotterysystem.app.R
import com.lotterysystem.app.base.BaseActivity
import com.lotterysystem.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {


    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
//        this@MainActivity.hideActionBar()
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }


    override fun subscribeCallbackFromSuccessStatus() {
        getResponse().observe(this, Observer {
            renderSuccessResponse(it)
        })
    }

    private fun renderSuccessResponse(response: Any?) {
        when (response) {

        }
    }


    fun navigate(resId: Int) {
        navController.navigate(resId)
    }

    fun navigate(directions: NavDirections?) {
        navController.navigate(directions!!)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}