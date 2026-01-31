package com.example.perpustakaan.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BukuDao {
    @Insert
    suspend fun insertBuku(buku: Buku)

    @Update
    suspend fun updateBuku(buku: Buku)

    @Query("SELECT * FROM buku WHERE isDeleted = 0 ORDER BY judul ASC")
    fun getAllBuku(): Flow<List<Buku>>

    @Query("SELECT * FROM buku WHERE idBuku = :id")
    suspend fun getBukuById(id: Int): Buku?

    @Query("SELECT * FROM buku WHERE kategoriId = :kategoriId AND isDeleted = 0")
    suspend fun getBukuByKategori(kategoriId: Int): List<Buku>
    

    @Query("SELECT COUNT(*) FROM buku WHERE kategoriId = :kategoriId AND status = 'dipinjam' AND isDeleted = 0")
    suspend fun countBorrowedBooksByCategory(kategoriId: Int): Int


    @Query("UPDATE buku SET isDeleted = 1 WHERE kategoriId = :kategoriId")
    suspend fun softDeleteBukuByKategori(kategoriId: Int)
}
