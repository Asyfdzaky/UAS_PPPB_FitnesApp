package com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Room.LatihanTable

@Database(entities = [LatihanTable::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Ensure TypeConverters are applied
abstract class LatihanRoomDatabase : RoomDatabase() {
    abstract fun latihanDao(): LatihanDao

    companion object {
        @Volatile
        private var INSTANCE: LatihanRoomDatabase? = null
        fun getDatabase(context: Context): LatihanRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(LatihanRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LatihanRoomDatabase::class.java,
                        "latihan_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
