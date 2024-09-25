package com.example.recipesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recipesapp.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)
        recyclerView = binding.recipeRecyclerView

        val viewModel: RecipeViewModel by activityViewModels()

        recipeAdapter = RecipeAdapter()
        recyclerView.adapter = recipeAdapter
        recyclerView.layoutManager =StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        viewModel.searchFavoriteRecipes().observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.setData(recipes)
        }

        return binding.root
    }

}