package com.venkatesh.shoppingdemo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.venkatesh.shoppingdemo.R
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.data.remote.model.Product
import com.venkatesh.shoppingdemo.databinding.ProductListItemBinding

class ProductAdapter(var context: Context,var list:List<Product>,var currentList:List<CartProducts>, var clickListener:(Product,View)->Unit): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(private val binding: ProductListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(
            context: Context,
            data: Product,
            clickListener: (Product,View) -> Unit
        ){
            binding.tvProductName.text = data.name
            binding.tvProductPrice.text = "₹"+data.price
            binding.rbProducts.rating = data.rating.toFloat()
            currentList.forEach {
                if(data.name.equals(it.name)){
                    binding.ivCart.setImageDrawable(AppCompatResources.getDrawable(context,R.drawable.ic_baseline_check_24))
                    binding.ivCart.isEnabled=false
                    return@forEach
                }
            }
            Glide.with(context)
                .load(data.imageUrl)
                .placeholder(AppCompatResources.getDrawable(context,R.drawable.preview1))
                .into(binding.ivProduct)

            binding.ivCart.setOnClickListener {
                clickListener(data,binding.root)
                binding.ivCart.setImageDrawable(AppCompatResources.getDrawable(context,R.drawable.ic_baseline_check_24))
                it.isClickable = false
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(context,list[position],clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}