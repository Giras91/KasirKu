package com.extropos.java.room.datasource

import android.content.Context
import com.extropos.java.room.AppDatabase
import com.extropos.java.room.dao.ProductDao
import com.extropos.java.room.entity.Product

class ProductDataSource(context: Context) {
    private val productDao: ProductDao = AppDatabase.getInstance(context).productDao()

    fun getAll(): List<com.extropos.java.entity.Product> {
        val roomProducts = productDao.getAll()
        val products = mutableListOf<com.extropos.java.entity.Product>()

        for (roomProduct in roomProducts) {
            val product = com.extropos.java.entity.Product().apply {
                productID = roomProduct.productId
                categoryID = roomProduct.categoryId
                productName = roomProduct.name
                description = roomProduct.description
                price = roomProduct.price
                discount = roomProduct.discount
                createdOn = roomProduct.createdOn
                updatedOn = roomProduct.updatedOn
                syncOn = roomProduct.syncOn
                createdBy = roomProduct.createdBy
                updatedBy = roomProduct.updatedBy
                merchantId = roomProduct.merchantId
                status = roomProduct.status
                refId = roomProduct.refId
                image = roomProduct.image
            }
            products.add(product)
        }

        return products
    }

    fun get(code: String): com.extropos.java.entity.Product? {
        val roomProduct = productDao.getById(code) ?: return null

        return com.extropos.java.entity.Product().apply {
            productID = roomProduct.productId
            categoryID = roomProduct.categoryId
            productName = roomProduct.name
            description = roomProduct.description
            price = roomProduct.price
            discount = roomProduct.discount
            createdOn = roomProduct.createdOn
            updatedOn = roomProduct.updatedOn
            syncOn = roomProduct.syncOn
            createdBy = roomProduct.createdBy
            updatedBy = roomProduct.updatedBy
            merchantId = roomProduct.merchantId
            status = roomProduct.status
            refId = roomProduct.refId
            image = roomProduct.image
        }
    }

    fun insert(product: com.extropos.java.entity.Product) {
        val roomProduct = Product(
            productId = product.productID,
            categoryId = product.categoryID,
            name = product.productName,
            description = product.description,
            price = product.price,
            discount = product.discount,
            createdOn = product.createdOn,
            updatedOn = product.updatedOn,
            syncOn = product.syncOn,
            createdBy = product.createdBy,
            updatedBy = product.updatedBy,
            merchantId = product.merchantId,
            status = product.status,
            refId = product.refId,
            image = product.image
        )

        productDao.insert(roomProduct)
    }

    fun update(product: com.extropos.java.entity.Product) {
        val roomProduct = Product(
            productId = product.productID,
            categoryId = product.categoryID,
            name = product.productName,
            description = product.description,
            price = product.price,
            discount = product.discount,
            createdOn = product.createdOn,
            updatedOn = product.updatedOn,
            syncOn = product.syncOn,
            createdBy = product.createdBy,
            updatedBy = product.updatedBy,
            merchantId = product.merchantId,
            status = product.status,
            refId = product.refId,
            image = product.image
        )

        productDao.update(roomProduct)
    }

    fun delete(product: com.extropos.java.entity.Product) {
        val roomProduct = Product(
            productId = product.productID,
            categoryId = product.categoryID,
            name = product.productName,
            description = product.description,
            price = product.price,
            discount = product.discount,
            createdOn = product.createdOn,
            updatedOn = product.updatedOn,
            syncOn = product.syncOn,
            createdBy = product.createdBy,
            updatedBy = product.updatedBy,
            merchantId = product.merchantId,
            status = product.status,
            refId = product.refId,
            image = product.image
        )

        productDao.delete(roomProduct)
    }

    fun truncate(): Long {
        productDao.deleteAll()
        return 0
    }
}