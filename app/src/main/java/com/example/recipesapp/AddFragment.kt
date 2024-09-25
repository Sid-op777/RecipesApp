package com.example.recipesapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recipesapp.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        val viewModel: RecipeViewModel by activityViewModels()

        binding.attachImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
        }



        binding.saveButton.setOnClickListener {
            val title = binding.titleField.editText?.text.toString()
            val ingredients = binding.ingredientsField.editText?.text.toString()
            val instructions = binding.instructionsField.editText?.text.toString()
            val category = binding.categoryField.editText?.text.toString()
            val isFavorite = binding.favSwitch.isChecked
            val image = selectedImageUri?.toString() // Store image URI

            val recipe = Recipe(title = title, ingredients = ingredients, instructions = instructions, category = category, image = image, isFavorite = isFavorite)
            viewModel.insert(recipe)

            // Reset fields
            binding.titleField.editText?.text?.clear()
            binding.ingredientsField.editText?.text?.clear()
            binding.instructionsField.editText?.text?.clear()
            binding.categoryField.editText?.text?.clear()
            selectedImageUri = null
            binding.favSwitch.isChecked = false
            binding.attachImage.setImageResource(R.drawable.header) // Reset image view
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.attachImage.setImageURI(uri)
            }
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST_CODE = 100
    }



}