package com.extropos.java.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.extropos.java.room.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE product_id = :productId")
    fun getById(productId: String): Product?

    @Query("SELECT * FROM product WHERE category_id = :categoryId")
    fun getByCategory(categoryId: String): List<Product>

    @Query("SELECT * FROM product WHERE status = :status")
    fun getByStatus(status: String): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM product")
    fun getCount(): Int
}