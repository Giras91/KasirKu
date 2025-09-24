package com.extropos.java.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.extropos.java.room.entity.ProductCategory

@Dao
interface ProductCategoryDao {
    @Query("SELECT * FROM product_category")
    fun getAll(): List<ProductCategory>

    @Query("SELECT * FROM product_category WHERE category_id = :categoryId")
    fun getById(categoryId: String): ProductCategory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: ProductCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<ProductCategory>)

    @Update
    fun update(category: ProductCategory)

    @Delete
    fun delete(category: ProductCategory)

    @Query("DELETE FROM product_category")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM product_category")
    fun getCount(): Int
}