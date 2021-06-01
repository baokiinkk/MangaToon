package com.baokiin.mangatoon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baokiin.mangatoon.data.model.Manga


@Database(entities = [Manga::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun appDao(): AppDao // bat buoc co

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // 3 công việc: check instance khác null thì return nó, null thì new ra + gán lại vào nó rồi return

            // full cú pháp kotlin
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }

            // cách if bth java
//            if (instance == null) {
//                synchronized(this) {
//                    if (instance == null) {
//                        instance = buildDatabase(context)
//                    }
//                }
//            }
//            return instance!!

        }

        // ham khoi tao database room
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "AppDB")
                .build()
        }
    }
}