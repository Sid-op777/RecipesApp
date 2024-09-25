package com.example.recipesapp

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDao) {
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    fun getRecipesByCategory(category: String): LiveData<List<Recipe>> {
        return recipeDao.getRecipesByCategory(category)
    }

    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    suspend fun update(recipe: Recipe) {
        recipeDao.update(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    fun searchRecipes(query: String, category: String): LiveData<List<Recipe>> {
        return if (query.isBlank()) {
            when (category) {
                "All" -> allRecipes
                "Breakfast" -> getRecipesByCategory("Breakfast")
                "Lunch" -> getRecipesByCategory("Lunch")
                "Dinner" -> getRecipesByCategory("Dinner")
                else -> allRecipes
            }
        } else {
            when (category) {
                "All" -> searchRecipesByTitleOrIngredients(query)
                "Breakfast" -> searchRecipesByTitleOrIngredientsAndCategory(query, "Breakfast")
                "Lunch" -> searchRecipesByTitleOrIngredientsAndCategory(query, "Lunch")
                "Dinner" -> searchRecipesByTitleOrIngredientsAndCategory(query, "Dinner")
                else -> searchRecipesByTitleOrIngredients(query)
            }
        }
    }

    fun searchFavoriteRecipes(): LiveData<List<Recipe>> {
        return recipeDao.searchFavoriteRecipes()
    }

    private fun searchRecipesByTitleOrIngredients(query: String): LiveData<List<Recipe>> {
        return recipeDao.searchRecipesByTitleOrIngredients("%$query%")
    }

    private fun searchRecipesByTitleOrIngredientsAndCategory(query: String, category: String): LiveData<List<Recipe>> {
        return recipeDao.searchRecipesByTitleOrIngredientsAndCategory("%$query%", category)
    }
}