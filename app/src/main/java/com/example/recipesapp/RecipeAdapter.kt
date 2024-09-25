package com.example.recipesapp

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes = emptyList<Recipe>()

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.recipeImage)
        val titleTextView: TextView = itemView.findViewById(R.id.recipeTitle)
        val favToggle: CheckBox = itemView.findViewById(R.id.fav_toggle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        Glide.with(holder.itemView.context)
            .load(currentRecipe.image)
            .into(holder.imageView)
        holder.titleTextView.text = currentRecipe.title
        holder.favToggle.isChecked = currentRecipe.isFavorite

        holder.itemView.setOnClickListener {

            val dialog = Dialog(holder.itemView.context, R.style.full_screen_dialog)
            dialog.setContentView(R.layout.popup_recipe)



            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

            val recipeImage = dialog.findViewById<ImageView>(R.id.recipe_image)
            val recipeTitle = dialog.findViewById<TextView>(R.id.recipe_title)
            val recipeIngredients = dialog.findViewById<TextView>(R.id.recipe_ingredients)
            val recipeInstructions = dialog.findViewById<TextView>(R.id.recipe_instructions)
            val recipeCategory = dialog.findViewById<TextView>(R.id.recipe_category)

            Glide.with(holder.itemView.context)
                .load(currentRecipe.image)
                .error(R.drawable.paneer_tikka)
                .into(recipeImage)

            recipeTitle.text = currentRecipe.title
            recipeIngredients.text = currentRecipe.ingredients
            recipeInstructions.text = currentRecipe.instructions
            recipeCategory.text = currentRecipe.category

            dialog.show()

        }

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

}