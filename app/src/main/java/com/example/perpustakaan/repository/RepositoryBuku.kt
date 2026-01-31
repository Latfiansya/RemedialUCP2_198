package com.example.perpustakaan.repository

import androidx.room.withTransaction
import com.example.perpustakaan.room.AuditLog
import com.example.perpustakaan.room.AuditLogDao
import com.example.perpustakaan.room.Buku
import com.example.perpustakaan.room.BukuDao
import com.example.perpustakaan.room.DatabasePerpustakaan
import com.example.perpustakaan.room.Kategori
import com.example.perpustakaan.room.KategoriDao
import kotlinx.coroutines.flow.Flow

class RepositoryBuku(
    private val bukuDao: BukuDao,
    private val kategoriDao: KategoriDao,
    private val auditLogDao: AuditLogDao,
    private val db: DatabasePerpustakaan
) {
    val allBuku: Flow<List<Buku>> = bukuDao.getAllBuku()
    val allKategori: Flow<List<Kategori>> = kategoriDao.getAllKategori()

    suspend fun insertBuku(buku: Buku) {
        db.withTransaction {
            bukuDao.insertBuku(buku)
            auditLogDao.insertLog(
                AuditLog(
                    entityName = "Buku",
                    beforeData = "null",
                    afterData = buku.toString()
                )
            )
        }
    }

    suspend fun insertKategori(kategori: Kategori) {

        if (kategori.parentId != null) {
            var currentParentId = kategori.parentId
            while (currentParentId != null) {
                if (currentParentId == kategori.idKategori) {
                    throw IllegalArgumentException("Cyclic reference detected!")
                }
                val parent = kategoriDao.getKategoriById(currentParentId)
                currentParentId = parent?.parentId
            }
        }

        db.withTransaction {
            kategoriDao.insertKategori(kategori)
            auditLogDao.insertLog(
                AuditLog(
                    entityName = "Kategori",
                    beforeData = "null",
                    afterData = kategori.toString()
                )
            )
        }
    }


    suspend fun deleteKategori(idKategori: Int) {
        db.withTransaction {

            val allCats = kategoriDao.getAllKategoriList()
            val categoriesToCheck = mutableListOf<Int>()
            categoriesToCheck.add(idKategori)
            

            var index = 0
            while(index < categoriesToCheck.size) {
                val currentId = categoriesToCheck[index]
                val children = allCats.filter { it.parentId == currentId }
                categoriesToCheck.addAll(children.map { it.idKategori })
                index++
            }

            var hasBorrowedBook = false
            for (catId in categoriesToCheck) {
                if (bukuDao.countBorrowedBooksByCategory(catId) > 0) {
                    hasBorrowedBook = true
                    break
                }
            }

            if (hasBorrowedBook) {
                throw IllegalStateException("Cannot delete: Contains borrowed books! Rollback triggered.")
            } else {
                 for (catId in categoriesToCheck) {
                    val k = kategoriDao.getKategoriById(catId)
                    kategoriDao.softDeleteKategori(catId)
                    bukuDao.softDeleteBukuByKategori(catId)
                    
                    if (k != null) {
                         auditLogDao.insertLog(
                            AuditLog(
                                entityName = "Kategori",
                                beforeData = k.toString(),
                                afterData = "Soft Deleted"
                            )
                        )
                    }
                }
            }
        }
    }
    
    suspend fun getBukuByKategori(kategoriId: Int): List<Buku> {
        return bukuDao.getBukuByKategori(kategoriId)
    }
}
