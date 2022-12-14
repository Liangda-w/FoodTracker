package com.example.foodtracker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracker.database.FoodItem
import com.example.foodtracker.databinding.ListItemBinding
import com.example.foodtracker.utils.formatExpirationDate

/**
 * ListAdapter uses AsyncListDiffer to determine the differences between an old list of data and a new list of data.
 * Then, the recycler view is only updated based on the differences between the two lists.
 */
class FoodItemAdapter(
    private val onItemClicked: (FoodItem) -> Unit
) : ListAdapter<FoodItem, FoodItemAdapter.FoodItemViewHolder>(DiffCallback) {

    companion object {
        /**
         * This is just object that helps the ListAdapter determine which items in the new and old lists are different when updating the list.
         */
        private val DiffCallback = object : DiffUtil.ItemCallback<FoodItem>() {
            /**
             * checks if the object (or row in the database in your case) is the same by only checking the ID
             */
            override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
                return oldItem.id == newItem.id
            }

            /**
             * checks if all properties, not just the ID, are the same.
             */
            override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        // inflate the layout
        val viewHolder = FoodItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Get item from the position in View
     */
    public override fun getItem(position: Int): FoodItem {
        return super.getItem(position)
    }

    class FoodItemViewHolder(
        private var binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(foodItem: FoodItem) {
            binding.foodItemTitle.text = foodItem.foodName
            binding.foodItemQuantity.text = foodItem.quantity.toString()
            formatExpirationDate(foodItem.foodExpirationDate, binding.foodItemExpirationDay)
            // TODO bind icon based on category
        }
    }
}

