package com.example.perpustakaan.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Buku::class, Kategori::class, AuditLog::class], version = 1, exportSchema = false)
abstract class DatabasePerpustakaan : RoomDatabase() {
    abstract fun bukuDao(): BukuDao
    abstract fun kategoriDao(): KategoriDao
    abstract fun auditLogDao(): AuditLogDao

    companion object {
        @Volatile
        private var Instance: DatabasePerpustakaan? = null

        fun getDatabase(context: Context): DatabasePerpustakaan {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DatabasePerpustakaan::class.java, "perpustakaan_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
