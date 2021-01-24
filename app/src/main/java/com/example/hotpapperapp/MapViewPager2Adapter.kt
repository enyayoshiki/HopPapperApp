package com.example.hotpapperapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.hotpapperapp.databinding.ItemViewpager2RestaurantMapBinding

class RestaurantMapRecycerViewAdapter(private val layoutInflater: LayoutInflater,private val context: Context) : RecyclerView.Adapter<RestaurantMapRecycerViewAdapter.ViewHolder>() {

    private val list = mutableListOf<Shop>()

    fun refresh(list: MutableList<Shop>) {

        list.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DataBindingUtil.inflate<ItemViewpager2RestaurantMapBinding>(
            layoutInflater, R.layout.item_viewpager2_restaurant_map, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(private val binding: ItemViewpager2RestaurantMapBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shop: Shop) {
            binding.shop = shop
        }
    }
}


