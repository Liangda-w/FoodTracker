package com.example.foodtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodtracker.database.FoodCategory
import com.example.foodtracker.database.FoodItem
import com.example.foodtracker.databinding.FragmentAddBinding
import com.example.foodtracker.utils.categorySpinnerToEnum
import com.example.foodtracker.utils.datePickerToLocalDate
import com.example.foodtracker.viewmodels.FoodItemViewModel
import com.example.foodtracker.viewmodels.FoodItemViewModelFactory

class AddFragment : Fragment() {
    // For Logging
    private val TAG = "AddFragment"

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

    private val navigationArgs: AddFragmentArgs by navArgs()
    lateinit var foodItem: FoodItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        _binding = DataBindingUtil.inflate<FragmentAddBinding>(
            inflater, R.layout.fragment_add, container, false
        )

        val values = FoodCategory.values().map { it.category }
        binding.categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            values
        )

        binding.expirationDatePicker.minDate = System.currentTimeMillis() - 1000
        return binding.root
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (validateFoodItemFromUserInput()) {
            viewModel.addNewFoodItem(
                binding.foodItemNameInput.text.toString(),
                datePickerToLocalDate(binding.expirationDatePicker),
                categorySpinnerToEnum(binding.categorySpinner),
                Integer.parseInt(binding.foodItemQuantityInput.text.toString())
            )
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                foodItem = selectedItem
                bind(foodItem)
            }
        } else {
            binding.addButton.setOnClickListener {
                addNewItem()
            }
        }
    }

    /**
     * Binds views with the passed in [FoodItem] information.
     */
    private fun bind(item: FoodItem) {
        binding.apply {
            foodItemNameInput.setText(item.foodName, TextView.BufferType.SPANNABLE)
            foodItemQuantityInput.setText(item.quantity.toString(), TextView.BufferType.SPANNABLE)
            categorySpinner.setSelection(item.category.ordinal)
            expirationDatePicker.updateDate(
                item.foodExpirationDate.year,
                item.foodExpirationDate.monthValue - 1,
                item.foodExpirationDate.dayOfMonth
            )
            addButton.text = getString(R.string.button_update)
            addButton.setOnClickListener { updateItem() }
        }
    }

    /**
     * Updates an existing Item in the database and navigates up to list fragment.
     */
    private fun updateItem() {
        if (validateFoodItemFromUserInput()) {
            viewModel.updateItem(
                this.navigationArgs.itemId,
                binding.foodItemNameInput.text.toString(),
                datePickerToLocalDate(binding.expirationDatePicker),
                categorySpinnerToEnum(binding.categorySpinner),
                Integer.parseInt(binding.foodItemQuantityInput.text.toString())
            )
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    /**
     * Validate the user input
     */
    private fun validateFoodItemFromUserInput(): Boolean {
        var valid = true

        // validate food name
        val name = binding.foodItemNameInput.text.toString()
        if (name == "") {
            binding.foodItemNameInputLayout.error = "The name shouldn't be empty"
            valid = false
        } else {
            binding.foodItemNameInputLayout.error = ""
        }
        // validate food quantity
        val quantity = binding.foodItemQuantityInput.text.toString()
        if (quantity == "") {
            binding.foodItemQuantityInputLayout.error = "The quantity shouldn't be empty"
            valid = false
        } else if (Integer.parseInt(quantity) <= 0){
            binding.foodItemQuantityInputLayout.error = "The quantity should be at least 1"
            valid = false
        } else {
            binding.foodItemQuantityInputLayout.error = ""
        }

        return valid
    }

}