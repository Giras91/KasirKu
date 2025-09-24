package com.extropos.java.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "product_category")
data class ProductCategory(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val categoryId: String,

    @ColumnInfo(name = "name")
    val name: String?
)