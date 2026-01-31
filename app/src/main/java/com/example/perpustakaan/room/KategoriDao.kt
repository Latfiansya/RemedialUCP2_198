package com.example.perpustakaan.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface KategoriDao {
    @Insert
    suspend fun insertKategori(kategori: Kategori)

    @Update
    suspend fun updateKategori(kategori: Kategori)

    @Query("SELECT * FROM kategori WHERE isDeleted = 0 ORDER BY namaKategori ASC")
    fun getAllKategori(): Flow<List<Kategori>>
    

    @Query("SELECT * FROM kategori WHERE isDeleted = 0")
    suspend fun getAllKategoriList(): List<Kategori>

    @Query("SELECT * FROM kategori WHERE idKategori = :id")
    suspend fun getKategoriById(id: Int): Kategori?

    @Query("UPDATE kategori SET isDeleted = 1 WHERE idKategori = :id")
    suspend fun softDeleteKategori(id: Int)
}
