package com.extropos.java.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.extropos.java.room.entity.TableService

@Dao
interface TableServiceDao {
    @Query("SELECT * FROM table_service")
    fun getAll(): List<TableService>

    @Query("SELECT * FROM table_service WHERE _id = :id")
    fun getById(id: Int): TableService?

    @Query("SELECT * FROM table_service WHERE table_id = :tableId")
    fun getByTableId(tableId: Int): List<TableService>

    @Query("SELECT * FROM table_service WHERE status = :status")
    fun getByStatus(status: String): List<TableService>

    @Query("SELECT * FROM table_service WHERE waiter_id = :waiterId")
    fun getByWaiterId(waiterId: String): List<TableService>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tableService: TableService): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tableServices: List<TableService>)

    @Update
    fun update(tableService: TableService)

    @Delete
    fun delete(tableService: TableService)

    @Query("DELETE FROM table_service")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM table_service")
    fun getCount(): Int
}