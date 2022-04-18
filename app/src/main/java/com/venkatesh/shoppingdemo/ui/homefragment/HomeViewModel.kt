package com.venkatesh.shoppingdemo.ui.homefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository):ViewModel() {

    val productResponse = repository.productResponse
    val productDetails = repository.productDetails

    fun getProductDetails(){
        viewModelScope.launch {
            repository.getProductDetails()
        }

    }

    fun insertProduct(products: CartProducts){
        viewModelScope.launch {
            repository.insertProduct(products)
        }
    }

    fun updateProductById(id: Int,qty:Int){
        viewModelScope.launch {
            repository.updateProductById(id,qty)
        }
    }

    fun updateCart(products: CartProducts){
        viewModelScope.launch {
            repository.updateCart(products)
        }
    }

}