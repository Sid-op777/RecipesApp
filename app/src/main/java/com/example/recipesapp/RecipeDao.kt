package com.example.recipesapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE category = :category")
    fun getRecipesByCategory(category: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE title LIKE :query OR ingredients LIKE :query")
    fun searchRecipesByTitleOrIngredients(query: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE (title LIKE :query OR ingredients LIKE :query) AND category = :category")
    fun searchRecipesByTitleOrIngredientsAndCategory(query: String, category: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE isFavorite = TRUE")
    fun searchFavoriteRecipes(): LiveData<List<Recipe>>

}