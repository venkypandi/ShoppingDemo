package com.venkatesh.shoppingdemo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.venkatesh.shoppingdemo.data.local.dao.CartDao
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.data.remote.api.ShoppingApiService
import com.venkatesh.shoppingdemo.data.remote.model.ProductResponse
import com.venkatesh.shoppingdemo.utils.Resource
import javax.inject.Inject


class ProductRepository @Inject constructor(private val shoppingService:ShoppingApiService,
                                            private val cartDao: CartDao) {

    private var _productResponse = MutableLiveData<Resource<ProductResponse>>()
    val productResponse:LiveData<Resource<ProductResponse>> = _productResponse

    val productDetails:LiveData<List<CartProducts>> = cartDao.getAllProducts()

    suspend fun getProductDetails() {
        _productResponse.value = Resource.loading(null)
        val response = shoppingService.getProductDetails()
        if (response.isSuccessful) {
            _productResponse.value = Resource.success(response.body())
        } else {
            _productResponse.value = Resource.error("No products found",null)
        }
    }
    suspend fun insertProduct(products: CartProducts){
        cartDao.insertProduct(products)
    }

    suspend fun deleteProductById(id:Int){
        cartDao.deleteById(id)
    }

    suspend fun updateProductById(id: Int,qty:Int){
        cartDao.updateProduct(id,qty)
    }

    suspend fun updateCart(products: CartProducts){
        cartDao.updateCart(products)
    }
}

