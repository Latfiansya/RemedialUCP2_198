package com.example.perpustakaan.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.room.Kategori
import com.example.perpustakaan.viewmodel.KategoriViewModel
import com.example.perpustakaan.viewmodel.provider.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanKategori(
    navigateBack: () -> Unit,
    viewModel: KategoriViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val listKategori by viewModel.listKategori.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val context = LocalContext.current

    var namaKategori by remember { mutableStateOf("") }
    var selectedParentId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Manajemen Kategori",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Add Form
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Tambah Kategori", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = namaKategori,
                        onValueChange = { namaKategori = it },
                        label = { Text("Nama Kategori") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Text("Parent (Opsional):")
                    Box {
                        val parentName = listKategori.find { it.idKategori == selectedParentId }?.namaKategori ?: "None (Root)"
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(parentName)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("None (Root)") },
                                onClick = { 
                                    selectedParentId = null
                                    expanded = false 
                                }
                            )
                            listKategori.forEach { k ->
                                DropdownMenuItem(
                                    text = { Text(k.namaKategori) },
                                    onClick = { 
                                        selectedParentId = k.idKategori
                                        expanded = false 
                                    }
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if(namaKategori.isNotBlank()){
                                viewModel.saveKategori(namaKategori, selectedParentId)
                                namaKategori = ""
                                selectedParentId = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Simpan Kategori")
                    }
                }
            }
            
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text("List Kategori (Flat/Hierarchical View)", style = MaterialTheme.typography.titleSmall)

            LazyColumn {
                items(listKategori) { kategori ->
                    // Determine depth simply by checking parents (inefficient for deep trees in UI loop but ok for basic)
                    // Better: ViewModel should expose a tree or depth-annotated list.
                    // For now, flat listing with Parent ID info.
                    KategoriItem(
                        kategori = kategori, 
                        parentName = listKategori.find { it.idKategori == kategori.parentId }?.namaKategori,
                        onDelete = { viewModel.deleteKategori(kategori.idKategori) }
                    )
                }
            }
        }
    }
}

@Composable
fun KategoriItem(kategori: Kategori, parentName: String?, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = kategori.namaKategori, style = MaterialTheme.typography.bodyLarge)
                if (parentName != null) {
                    Text(text = "Parent: $parentName", style = MaterialTheme.typography.labelSmall)
                } else {
                    Text(text = "Root Category", style = MaterialTheme.typography.labelSmall)
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}
