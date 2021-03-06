package com.venkatesh.shoppingdemo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(products: CartProducts)

    @Delete
    suspend fun deleteProducts(products: CartProducts)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM cart_table")
    fun getAllProducts():LiveData<List<CartProducts>>

    @Query("UPDATE cart_table SET qty=:quantity WHERE id=:id")
    suspend fun updateProduct(id:Int,quantity:Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCart(products: CartProducts):Int

}