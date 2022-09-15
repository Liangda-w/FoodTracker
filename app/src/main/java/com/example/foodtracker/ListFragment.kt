package com.example.foodtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracker.adapter.FoodItemAdapter
import com.example.foodtracker.database.FoodItem
import com.example.foodtracker.databinding.FragmentListBinding
import com.example.foodtracker.viewmodels.FoodItemViewModel
import com.example.foodtracker.viewmodels.FoodItemViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class ListFragment : Fragment() {
    // For Logging
    private val TAG = "ListFragment"

    private lateinit var recyclerView: RecyclerView

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodItemViewModel by activityViewModels {
        FoodItemViewModelFactory(
            (activity?.application as FoodItemApplication).database.foodItemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )

        binding.addFloatingActionButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // TODO Navigate to edit screen
        val foodItemAdapter = FoodItemAdapter {
            view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        recyclerView.adapter = foodItemAdapter
        lifecycle.coroutineScope.launch {
            viewModel.foodList().collect() {
                foodItemAdapter.submitList(it)
            }
        }

        /**
         * Swap the item to left to delete the item
         */
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called when the item is moved.
                return false
            }

            // this method is called when we swipe our item to left direction.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get the position of the item
                val position = viewHolder.adapterPosition

                // get the item at the particular position.
                val deletedFoodItem: FoodItem = foodItemAdapter.getItem(position)

                // delete the item
                viewModel.deleteItem(deletedFoodItem)

                // display our snackbar with action.
                Snackbar.make(
                    recyclerView,
                    "Deleted " + deletedFoodItem.foodName,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(
                        "Undo",
                        // adding on click listener to our action of snack bar.
                        View.OnClickListener {
                            // add the deleted item back
                            viewModel.insetItem(deletedFoodItem)
                        }).show()
            }
            // at last we are adding this to our recycler view.
        }).attachToRecyclerView(recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}