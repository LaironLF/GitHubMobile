package com.laironlf.githubmobile.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.laironlf.githubmobile.R
import com.laironlf.githubmobile.domain.repository.AppRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appRepository: AppRepository
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findNavController()
        setupToolbar()
        if (onSplashScreenLogin()) navController.navigate(R.id.action_authorizationFragment_to_home_nav)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }

    private fun onSplashScreenLogin(): Boolean {
        var isSplashLogged: Boolean
        runBlocking { isSplashLogged = appRepository.signInByStorageToken() }
        return isSplashLogged
    }

    private fun onExitButtonClicked() {
        appRepository.clearLoginData()
        navController.navigate(R.id.authorizationFragment)
    }

    private fun setupExitButton() {
        val exitButton: ImageView = findViewById(R.id.exit_button)
        exitButton.setOnClickListener { onExitButtonClicked() }
    }

    private fun findNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setupExitButton()
        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.repositoriesFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _: NavController, navDestination: NavDestination, _: Bundle? ->
            toolbar.visibility =
                if (navDestination.id != R.id.authorizationFragment) View.VISIBLE else View.GONE
        }
    }
}