package com.example.perpustakaan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.repository.RepositoryBuku
import com.example.perpustakaan.room.Buku
import com.example.perpustakaan.room.Kategori
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EntryBukuViewModel(private val repository: RepositoryBuku) : ViewModel() {
    
    val listKategori: StateFlow<List<Kategori>> = repository.allKategori
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun saveBuku(judul: String, status: String, kategoriId: Int) {
        viewModelScope.launch {
            val buku = Buku(
                judul = judul,
                status = status,
                kategoriId = kategoriId
            )
            repository.insertBuku(buku)
        }
    }
}
