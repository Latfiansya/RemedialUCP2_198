package com.example.perpustakaan.repository

import android.app.Application
import com.example.perpustakaan.room.DatabasePerpustakaan

class ContainerApp : Application() {
    lateinit var repositoryBuku: RepositoryBuku

    override fun onCreate() {
        super.onCreate()
        val database = DatabasePerpustakaan.getDatabase(this)
        repositoryBuku = RepositoryBuku(
            database.bukuDao(),
            database.kategoriDao(),
            database.auditLogDao(),
            database
        )
    }
}
