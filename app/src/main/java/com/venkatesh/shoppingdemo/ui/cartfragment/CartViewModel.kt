package com.venkatesh.shoppingdemo.ui.cartfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val productDetails = repository.productDetails

    fun insertProducts(products: CartProducts){
        viewModelScope.launch {
            repository.insertProduct(products)
        }
    }

    fun deleteProductById(id:Int){
        viewModelScope.launch {
            repository.deleteProductById(id)
        }
    }

    fun updateProductById(id: Int,qty:Int){
        viewModelScope.launch {
            repository.updateProductById(id,qty)
        }
    }
}