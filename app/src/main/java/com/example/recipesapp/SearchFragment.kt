package com.example.recipesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recipesapp.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayout
import kotlin.text.trim


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        recyclerView = binding.searchRecyclerView

        recipeAdapter = RecipeAdapter()
        recyclerView.adapter = recipeAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        val viewModel: RecipeViewModel by activityViewModels()

        // Set up search functionality
        binding.searchEditText.addTextChangedListener { editable ->
            val searchQuery = editable.toString().trim()
            val selectedCategory = when (binding.categoryTabLayout.selectedTabPosition) {
                0 -> "All"
                1 -> "Breakfast"
                2 -> "Lunch"
                3 -> "Dinner"
                else -> "All"
            }
            viewModel.searchRecipes(searchQuery, selectedCategory).observe(viewLifecycleOwner) { recipes ->
                recipeAdapter.setData(recipes)
            }
        }

        binding.categoryTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Perform search with the selected category
                val searchQuery = binding.searchEditText.text.toString().trim()
                val selectedCategory = when (tab?.position) {
                    0 -> "All"
                    1 -> "Breakfast"
                    2 -> "Lunch"
                    3 -> "Dinner"
                    else -> "All"
                }
                viewModel.searchRecipes(searchQuery, selectedCategory).observe(viewLifecycleOwner) { recipes ->
                    recipeAdapter.setData(recipes)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


        return binding.root
    }


}