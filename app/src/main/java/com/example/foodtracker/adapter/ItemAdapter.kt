package com.example.foodtracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracker.R
import com.example.foodtracker.model.FoodItem

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [FoodItem] data object.
 */
class ItemAdapter(
    private val context: Context,
    private val dataset: List<FoodItem>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    /**
    * Provide a reference to the views for each data item
    * Complex data items may need more than one view per item, and
    * you provide access to all the views for a data item in a view holder.
    * Each data item is just an FoodItem object.
    */
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.food_item_title)
        val iconView: ImageView = view.findViewById(R.id.food_item_icon)
        val expirationTextView: TextView = view.findViewById(R.id.food_item_expiration_day)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.titleTextView.text = context.resources.getString(item.stringResourceId)
        holder.iconView.setImageResource(item.iconResourceId)
        holder.expirationTextView.text = item.expirationIn.toString()
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size

}