package com.example.foodtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodtracker.database.FoodCategory
import com.example.foodtracker.database.FoodItem
import com.example.foodtracker.databinding.FragmentAddBinding
import com.example.foodtracker.viewmodels.FoodItemViewModel
import com.example.foodtracker.viewmodels.FoodItemViewModelFactory
import java.time.LocalDate

class AddFragment : Fragment() {
    // Binding object instance corresponding to the fragment_add.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodItemViewModel by activityViewModels {
        FoodItemViewModelFactory(
            (activity?.application as FoodItemApplication).database.foodItemDao()
        )
    }

    lateinit var foodItem: FoodItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        _binding = DataBindingUtil.inflate<FragmentAddBinding>(
            inflater, R.layout.fragment_add, container, false
        )
        return binding.root
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        viewModel.addNewFoodItem(
            binding.foodItemNameInput.text.toString(),
            LocalDate.now(),
            FoodCategory.CEREALS_AND_LEGUMES,
            Integer.parseInt(binding.foodItemQuantityInput.text.toString())
        )
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            addNewItem()
        }
    }

}