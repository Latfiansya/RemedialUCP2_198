package com.example.perpustakaan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.repository.RepositoryBuku
import com.example.perpustakaan.room.Kategori
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class KategoriViewModel(private val repository: RepositoryBuku) : ViewModel() {
    
    val listKategori: StateFlow<List<Kategori>> = repository.allKategori
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    fun saveKategori(nama: String, parentId: Int?) {
        viewModelScope.launch {
            try {
                val kategori = Kategori(
                    namaKategori = nama,
                    parentId = parentId
                )
                repository.insertKategori(kategori)
                _toastMessage.value = "Kategori berhasil disimpan"
            } catch (e: Exception) {
                _toastMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteKategori(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteKategori(id)
                _toastMessage.value = "Kategori dihapus (Soft Delete)"
            } catch (e: Exception) {
                 _toastMessage.value = "Gagal Hapus: ${e.message}"
            }
        }
    }
    
    fun clearToast() {
        _toastMessage.value = null
    }
}
