package com.selamikadioglu.native_mobile_case_study.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.selamikadioglu.native_mobile_case_study.local.dao.FavoriteProductDao
import com.selamikadioglu.native_mobile_case_study.local.dao.OrderDao
import com.selamikadioglu.native_mobile_case_study.local.table.FavoriteProduct
import com.selamikadioglu.native_mobile_case_study.local.table.Order

@Database(entities = [ FavoriteProduct::class, Order::class], version = 4, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun favoriteProductDao(): FavoriteProductDao
    abstract fun orderDao(): OrderDao


}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
    }
}