package com.example.perpustakaan.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AuditLogDao {
    @Insert
    suspend fun insertLog(log: AuditLog)

    @Query("SELECT * FROM audit_log ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<AuditLog>>
}
