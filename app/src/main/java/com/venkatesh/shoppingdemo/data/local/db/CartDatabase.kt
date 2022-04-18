package com.venkatesh.shoppingdemo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.venkatesh.shoppingdemo.data.local.dao.CartDao
import com.venkatesh.shoppingdemo.data.local.entity.CartProducts

@Database(entities = [CartProducts::class], version = 3, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao():CartDao
}