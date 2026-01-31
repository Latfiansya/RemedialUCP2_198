package com.example.perpustakaan.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_log")
data class AuditLog(
    @PrimaryKey(autoGenerate = true)
    val idLog: Int = 0,
    val entityName: String, // Changed from 'entity' to 'entityName' to avoid keyword confusion
    val beforeData: String,
    val afterData: String,
    val timestamp: Long = System.currentTimeMillis()
)
