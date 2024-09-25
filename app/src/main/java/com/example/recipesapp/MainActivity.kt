package com.example.recipesapp

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.recipesapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val repository =RecipeRepository(RecipeDatabase.getDatabase(this).recipeDao())
        viewModel = ViewModelProvider(this, RecipeViewModelFactory(repository))[RecipeViewModel::class.java]

        //use a database size maybe
        val recipeDao = RecipeDatabase.getDatabase(this).recipeDao()
//        if (recipeDao.getAllRecipes().value.isNullOrEmpty()) { // Check if the database is empty
//            lifecycleScope.launch {
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 1",
//                        ingredients = "Ingredients 1",
//                        instructions = "Instructions 1",
//                        category = "Breakfast"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 2",
//                        ingredients = "Ingredients 2",
//                        instructions = "Instructions 2",
//                        category = "Lunch"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 3",
//                        ingredients = "Ingredients 3",
//                        instructions = "Instructions 3",
//                        category = "Dinner"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 4",
//                        ingredients = "Ingredients 4",
//                        instructions = "Instructions 4",
//                        category = "Dessert"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 5",
//                        ingredients = "Ingredients 5",
//                        instructions = "Instructions 5",
//                        category = "Soup"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 6",
//                        ingredients = "Ingredients 6",
//                        instructions = "Instructions 6",
//                        category = "Breakfast"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 7",
//                        ingredients = "Ingredients 7",
//                        instructions = "Instructions 7",
//                        category = "Dinner"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 8",
//                        ingredients = "Ingredients 8",
//                        instructions = "Instructions 8",
//                        category = "Lunch"
//                    ))
//                recipeDao.insert(
//                    Recipe(
//                        title = "Recipe 9",
//                        ingredients = "Ingredients 9",
//                        instructions = "Instructions 9",
//                        category = "Appetizer"
//                    ))
//            }
//        }

        val btmNav: BottomNavigationView = binding.bottomNavigation

        btmNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btm_nav_home -> {
                    navController.popBackStack(R.id.homeFragment, false)
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.btm_nav_search -> {
                    navController.popBackStack(R.id.homeFragment,false)
                    navController.navigate(R.id.searchFragment)
                    true
                }
                R.id.btm_nav_add -> {
                    navController.popBackStack(R.id.homeFragment,false)
                    navController.navigate(R.id.addFragment)
                    true
                }
                R.id.btm_nav_favourites -> {
                    navController.popBackStack(R.id.homeFragment,false)
                    navController.navigate(R.id.favouritesFragment)
                    true
                }
                R.id.btm_nav_profile -> {
                    navController.popBackStack(R.id.homeFragment,false)
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }

        this.onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed(){
                if(btmNav.selectedItemId!=R.id.btm_nav_home){
                    btmNav.selectedItemId = R.id.btm_nav_home
                }
                else{
                    finish()
                }
            }
        })
    }
}