package com.venkatesh.shoppingdemo.ui.homefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkatesh.shoppingdemo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository):ViewModel() {

    val productResponse = repository.productResponse

    fun getProductDetails(){
        viewModelScope.launch {
            repository.getProductDetails()
        }

    }

}