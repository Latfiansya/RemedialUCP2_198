package com.example.perpustakaan.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "buku",
    foreignKeys = [
        ForeignKey(
            entity = Kategori::class,
            parentColumns = ["idKategori"],
            childColumns = ["kategoriId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["kategoriId"])]
)
data class Buku(
    @PrimaryKey(autoGenerate = true)
    val idBuku: Int = 0,
    val judul: String,
    val status: String, // "tersedia" or "dipinjam"
    val kategoriId: Int,
    val isDeleted: Boolean = false
)
