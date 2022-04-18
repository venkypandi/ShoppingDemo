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

    fun deleteProducts(cartProducts: CartProducts){
        viewModelScope.launch {
            repository.deleteProducts(cartProducts)
        }
    }

    fun updateCart(products: CartProducts){
        viewModelScope.launch {
            repository.updateCart(products)
        }
    }
}