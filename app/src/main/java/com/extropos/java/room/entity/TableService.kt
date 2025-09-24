package com.extropos.java.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "table_service")
data class TableService(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,

    @ColumnInfo(name = "table_id")
    val tableId: Int,

    @ColumnInfo(name = "table_name")
    val tableName: String?,

    @ColumnInfo(name = "status")
    val status: String?,

    @ColumnInfo(name = "waiter_id")
    val waiterId: String?,

    @ColumnInfo(name = "waiter_name")
    val waiterName: String?,

    @ColumnInfo(name = "customer_name")
    val customerName: String?,

    @ColumnInfo(name = "customer_phone")
    val customerPhone: String?,

    @ColumnInfo(name = "service_start_time")
    val serviceStartTime: Long,

    @ColumnInfo(name = "service_end_time")
    val serviceEndTime: Long,

    @ColumnInfo(name = "reservation_time")
    val reservationTime: Long,

    @ColumnInfo(name = "special_requests")
    val specialRequests: String?,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "estimated_bill")
    val estimatedBill: Double,

    @ColumnInfo(name = "guest_count")
    val guestCount: Int
)