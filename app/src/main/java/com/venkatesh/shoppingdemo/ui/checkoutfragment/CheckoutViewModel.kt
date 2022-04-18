package com.venkatesh.shoppingdemo.ui.checkoutfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkatesh.shoppingdemo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private var repository: ProductRepository):ViewModel() {


    fun deleteAllProducts(){
        viewModelScope.launch {
            repository.deleteAllProducts()
        }
    }
}