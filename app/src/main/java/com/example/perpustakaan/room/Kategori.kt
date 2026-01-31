package com.example.perpustakaan.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kategori")
data class Kategori(
    @PrimaryKey(autoGenerate = true)
    val idKategori: Int = 0,
    val namaKategori: String,
    val parentId: Int? = null, // Nullable for root categories
    val isDeleted: Boolean = false
)
