package com.example.perpustakaan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpustakaan.repository.RepositoryBuku
import com.example.perpustakaan.room.Buku
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailBukuViewModel(private val repository: RepositoryBuku) : ViewModel() {
    
    private val _buku = MutableStateFlow<Buku?>(null)
    val buku: StateFlow<Buku?> = _buku

    fun getBukuById(id: Int) {
        viewModelScope.launch {
            // Since DAO for getBukuById is suspend, we launch
            // We need to access DAO. But repository exposes Flow primarily.
            // I need to add suspend getBuku to Repository or use what I have.
            // RepositoryBuku currently has `allBuku`: Flow.
            // I should have added `getBukuById` to Repository.
            // For now, I'll filter from the flow or add it to repo. 
            // Adding to repo is cleaner.
            
            // Let's assume I add it to Repo in the next step or I use a quick hack if I can't edit Repo easily.
            // Actually I can edit Repo easily. I will add getBukuById to RepositoryBuku.
            // Wait, I can't edit Repo in the same "write" call group if I want to be safe, but I can do it.
            // Or I can just observe allBuku and find. For a student app, finding in list is fine.
            
            repository.allBuku.collect { list ->
                _buku.value = list.find { it.idBuku == id }
            }
        }
    }
}
