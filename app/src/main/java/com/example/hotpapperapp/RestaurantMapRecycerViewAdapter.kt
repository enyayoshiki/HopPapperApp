package com.example.hotpapperapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hotpapperapp.databinding.ItemViewpager2RestaurantMapBinding
import timber.log.Timber

class RestaurantMapRecycerViewAdapter : RecyclerView.Adapter<RestaurantMapRecycerViewAdapter.ViewHolder>() {

    private val shopData = mutableListOf<Shop>()

    fun refresh(list: MutableList<Shop>) {

        shopData.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = shopData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DataBindingUtil.inflate<ItemViewpager2RestaurantMapBinding>(
            LayoutInflater.from(parent.context), R.layout.item_viewpager2_restaurant_map, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = shopData[position]
        Timber.i("Timber data: $data")
        holder.bind(data)
    }


    class ViewHolder(private val binding: ItemViewpager2RestaurantMapBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(shop: Shop) {
            binding.shop = shop
        }
    }
}


