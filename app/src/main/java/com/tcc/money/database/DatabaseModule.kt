package com.tcc.money.database

import android.content.Context
import androidx.room.Room
import com.tcc.money.database.DataBase
import com.tcc.money.database.dao.CoinsDao
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.database.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideCoinsDao(db: DataBase): CoinsDao {
        return db.coinsDao()
    }

    @Provides
    fun provideMovementsDao(db: DataBase): MovementsDao {
        return db.movementsDao()
    }

    @Provides
    fun provideUsersDao(db: DataBase): UsersDao {
        return db.usersDao()
    }
}