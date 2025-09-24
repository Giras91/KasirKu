package com.extropos.java.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: String,

    @ColumnInfo(name = "category_id")
    val categoryId: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "discount")
    val discount: Double,

    @ColumnInfo(name = "created_on")
    val createdOn: String?,

    @ColumnInfo(name = "updated_on")
    val updatedOn: String?,

    @ColumnInfo(name = "sycn_on")
    val syncOn: String?,

    @ColumnInfo(name = "created_by")
    val createdBy: String?,

    @ColumnInfo(name = "updated_by")
    val updatedBy: String?,

    @ColumnInfo(name = "merchant_id")
    val merchantId: String?,

    @ColumnInfo(name = "status")
    val status: String?,

    @ColumnInfo(name = "ref_id")
    val refId: String?,

    @ColumnInfo(name = "image")
    val image: String?
)