package com.extropos.java.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "password")
    val password: String?,

    @ColumnInfo(name = "level")
    val level: String?,

    @ColumnInfo(name = "last_login")
    val lastLogin: String?,

    @ColumnInfo(name = "cashier_id")
    val cashierId: String?
)