package com.extropos.java.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.extropos.java.room.dao.ProductDao
import com.extropos.java.room.dao.ProductCategoryDao
import com.extropos.java.room.dao.UserDao
import com.extropos.java.room.dao.TableServiceDao
import com.extropos.java.room.entity.Product
import com.extropos.java.room.entity.ProductCategory
import com.extropos.java.room.entity.User
import com.extropos.java.room.entity.TableService

@Database(
    entities = [
        Product::class,
        ProductCategory::class,
        User::class,
        TableService::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun userDao(): UserDao
    abstract fun tableServiceDao(): TableServiceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "com_chipo_cashier.db"
                )
                    .addMigrations(MIGRATION_4_5)
                    .build()
                    .also { INSTANCE = it }
            }
        }

        // Migration from version 4 to 5 (adding Room entities)
        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // The tables already exist from the old SQLite implementation
                // Room will handle the schema validation
            }
        }
    }
}