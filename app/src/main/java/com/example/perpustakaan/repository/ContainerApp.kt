package com.example.perpustakaan.repository

import android.app.Application
import android.util.Log
import com.example.perpustakaan.room.DatabasePerpustakaan

class ContainerApp : Application() {
    

    lateinit var repositoryBuku: RepositoryBuku
        private set

    override fun onCreate() {
        super.onCreate()
        Log.d("ContainerApp", "Application onCreate started")
        
        val database = DatabasePerpustakaan.getDatabase(this)
        Log.d("ContainerApp", "Database initialized")
        
        repositoryBuku = RepositoryBuku(
            database.bukuDao(),
            database.kategoriDao(),
            database.auditLogDao(),
            database
        )
        Log.d("ContainerApp", "Repository initialized, Application ready")
    }
}
