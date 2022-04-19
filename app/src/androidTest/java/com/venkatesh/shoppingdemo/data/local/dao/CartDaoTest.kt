package com.venkatesh.shoppingdemo.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.venkatesh.shoppingdemo.data.local.db.CartDatabase
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts
import com.venkatesh.shoppingdemo.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
@OptIn(ExperimentalCoroutinesApi::class)
class CartDaoTest {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()
    private lateinit var database:CartDatabase
    private lateinit var cartDao: CartDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CartDatabase::class.java
        ).allowMainThreadQueries().build()
        cartDao = database.cartDao()
    }


    @Test
    fun insertProduct(){
        runBlocking {
            val cartProducts = CartProducts(
                id = 1, imageUrl = "abc", name = "abc", price = "abc", rating = 1, qty = 2
            )
            cartDao.insertProduct(cartProducts)

            val allProducts = cartDao.getAllProducts().getOrAwaitValue()
            Truth.assertThat(allProducts).contains(cartProducts)

        }
    }

    @Test
    fun deleteProduct(){
        runBlockingTest {
            val cartProducts = CartProducts(
                id = 1, imageUrl = "abc", name = "abc", price = "abc", rating = 1, qty = 2
            )
            cartDao.insertProduct(cartProducts)
            cartDao.deleteProducts(cartProducts)

            val allProducts = cartDao.getAllProducts().getOrAwaitValue()
            Truth.assertThat(allProducts).doesNotContain(cartProducts)
        }
    }

    @Test
    fun deleteAllProducts(){
        runBlockingTest {
            val cartProducts = CartProducts(
                id = 1, imageUrl = "abc", name = "abc", price = "abc", rating = 1, qty = 2
            )
            val cartProducts1 = CartProducts(
                id = 2, imageUrl = "abc", name = "abc", price = "abc", rating = 1, qty = 2
            )
            val cartProducts2 = CartProducts(
                id = 3, imageUrl = "abc", name = "abc", price = "abc", rating = 1, qty = 2
            )

            cartDao.insertProduct(cartProducts)
            cartDao.insertProduct(cartProducts1)
            cartDao.insertProduct(cartProducts2)

            cartDao.deleteAllProducts()

            val allProducts = cartDao.getAllProducts().getOrAwaitValue()
            Truth.assertThat(allProducts).doesNotContain(cartProducts)
            Truth.assertThat(allProducts).doesNotContain(cartProducts1)
            Truth.assertThat(allProducts).doesNotContain(cartProducts2)

        }
    }

    @Test
    fun updateProduct(){
        runBlockingTest {
            val cartProducts = CartProducts(
                id = 1, imageUrl = "abc", name = "abc", price = "abc", rating = 1, qty = 2
            )
            cartDao.insertProduct(cartProducts)
            cartDao.updateCart(
                CartProducts(
                    id = 1, imageUrl = "cde", name = "jfn", price = "", rating = 0, qty = 2
                )
            )
            val allProducts = cartDao.getAllProducts().getOrAwaitValue()
            Truth.assertThat(allProducts.get(0).imageUrl).contains("cde")
        }


    }




}