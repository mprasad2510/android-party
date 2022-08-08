package com.android.nortontestapplication.database

import android.content.Context
import androidx.room.*
import com.android.nortontestapplication.model.ServersListItem

@Database(entities = [ServersListItem::class], version = 1, exportSchema = false)
@TypeConverters(AppTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val serverDAO : AppDAO

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context: Context):AppDatabase{
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "norton_app_database"
                    ).allowMainThreadQueries()
                        .build()
                }
                return instance
            }
        }
    }
}