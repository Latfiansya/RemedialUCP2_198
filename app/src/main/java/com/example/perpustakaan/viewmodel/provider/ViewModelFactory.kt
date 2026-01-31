package com.example.perpustakaan.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perpustakaan.repository.ContainerApp
import com.example.perpustakaan.viewmodel.DetailBukuViewModel
import com.example.perpustakaan.viewmodel.EntryBukuViewModel
import com.example.perpustakaan.viewmodel.HomeViewModel
import com.example.perpustakaan.viewmodel.KategoriViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                perpustakaanApplication().repositoryBuku
            )
        }
        initializer {
            EntryBukuViewModel(
                perpustakaanApplication().repositoryBuku
            )
        }
        initializer { 
            KategoriViewModel(
                perpustakaanApplication().repositoryBuku
            )
        }
        initializer {
            DetailBukuViewModel(
                perpustakaanApplication().repositoryBuku
            )
        }
    }
}

fun CreationExtras.perpustakaanApplication(): ContainerApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ContainerApp)
