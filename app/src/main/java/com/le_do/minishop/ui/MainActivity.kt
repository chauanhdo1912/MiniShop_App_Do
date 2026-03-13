package com.le_do.minishop.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.le_do.minishop.R
import androidx.navigation.ui.NavigationUI
import android.view.View

// MainActivity: startet die App und verwaltet die Navigation
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Anpassung für Systemleisten (Statusbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigation und BottomNavigation einrichten
        setupNavigation()
    }

    // Verbindet BottomNavigation mit dem NavController
    private fun setupNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        NavigationUI.setupWithNavController(bottomNav, navController)

        // BottomNavigation bei Login und Register ausblenden
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.loginFragment,
                R.id.registerFragment -> {
                    bottomNav.visibility = View.GONE
                }

                else -> {
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }
}