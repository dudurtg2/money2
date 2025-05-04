package com.tcc.money.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tcc.money.database.dao.CoinsDao
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.database.dao.UsersDao
import com.tcc.money.database.entities.CoinsEntity
import com.tcc.money.database.entities.MovementsEntity
import com.tcc.money.database.entities.UsersEntity

@Database(entities = [CoinsEntity::class, MovementsEntity::class, UsersEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun coinsDao(): CoinsDao
    abstract fun movementsDao(): MovementsDao
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "money_data_base"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}