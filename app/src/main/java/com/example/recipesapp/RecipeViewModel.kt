package com.example.recipesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    val allRecipes: LiveData<List<Recipe>> = repository.allRecipes

    fun getRecipesByCategory(category: String): LiveData<List<Recipe>> {
        return repository.getRecipesByCategory(category)
    }

    fun insert(recipe: Recipe) =
        viewModelScope.launch {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) = viewModelScope.launch {
        repository.delete(recipe)
    }

    fun searchRecipes(searchQuery: String, selectedCategory: String):  LiveData<List<Recipe>> {
        return repository.searchRecipes(searchQuery,selectedCategory)
    }

    fun searchFavoriteRecipes(): LiveData<List<Recipe>> {
        return repository.searchFavoriteRecipes()
    }



}