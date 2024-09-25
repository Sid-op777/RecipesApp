package com.example.recipesapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val ingredients: String,
    val instructions: String,
    val category: String,
    val image: String? = null,
    var isFavorite: Boolean = false,
    val rating: Float = 0f
)