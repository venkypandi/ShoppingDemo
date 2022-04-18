package com.venkatesh.shoppingdemo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.venkatesh.shoppingdemo.R
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.databinding.CartListItemBinding

class CartAdapter(var context: Context,var list: List<CartProducts>,var clickListener:(CartProducts,Int)->Unit)
    :RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

        inner class CartViewHolder(private val binding: CartListItemBinding):RecyclerView.ViewHolder(binding.root){

            fun bind(
                context: Context,
                data:CartProducts,
                clickListener: (CartProducts,Int) -> Unit
                ){
                binding.tvProductName.text = data.name
                binding.tvProductPrice.text = "₹"+data.price
                Glide.with(context)
                    .load(data.imageUrl)
                    .placeholder(AppCompatResources.getDrawable(context, R.drawable.preview1))
                    .into(binding.ivProduct)
                binding.tvQty.text = data.qty.toString()

                binding.ivPlus.setOnClickListener {
                    clickListener(data,1)
                }

                binding.ivMinus.setOnClickListener {
                    if(data.qty==1){
                        clickListener(data,-1)
                    }else{
                        clickListener(data,0)
                    }
                }


            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(context,list[position],clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}