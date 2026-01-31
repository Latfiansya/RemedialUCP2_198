package com.example.perpustakaan.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.room.Buku
import com.example.perpustakaan.viewmodel.HomeViewModel
import com.example.perpustakaan.viewmodel.provider.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
    navigateToEntry: () -> Unit,
    navigateToKategori: () -> Unit,
    onBukuClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val listBuku by viewModel.listBuku.collectAsState()

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Daftar Buku Univ",
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = navigateToKategori,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Filled.List, contentDescription = "Manage Kategori")
                }
                FloatingActionButton(onClick = navigateToEntry) {
                    Icon(Icons.Filled.Add, contentDescription = "Tambah Buku")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (listBuku.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada data buku")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listBuku, key = { it.idBuku }) { buku ->
                        BukuItem(buku = buku, onClick = { onBukuClick(buku.idBuku) })
                    }
                }
            }
        }
    }
}

@Composable
fun BukuItem(buku: Buku, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = buku.judul, style = MaterialTheme.typography.titleMedium)
            Text(text = "Status: ${buku.status}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
