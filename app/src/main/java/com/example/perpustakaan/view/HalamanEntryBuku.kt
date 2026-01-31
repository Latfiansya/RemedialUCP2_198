package com.example.perpustakaan.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.viewmodel.EntryBukuViewModel
import com.example.perpustakaan.viewmodel.provider.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntryBuku(
    navigateBack: () -> Unit,
    viewModel: EntryBukuViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val listKategori by viewModel.listKategori.collectAsState()
    var judul by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("tersedia") }
    var selectedKategoriId by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    val statusOptions = listOf("tersedia", "dipinjam")

    // Set default category
    if (listKategori.isNotEmpty() && selectedKategoriId == 0) {
        selectedKategoriId = listKategori.first().idKategori
    }

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Tambah Buku",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = judul,
                onValueChange = { judul = it },
                label = { Text("Judul Buku") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text("Status:", style = MaterialTheme.typography.labelLarge)
            Row {
                statusOptions.forEach { text ->
                    Row(
                        Modifier
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedStatus),
                                onClick = { selectedStatus = text },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedStatus),
                            onClick = null 
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Text("Kategori:", style = MaterialTheme.typography.labelLarge)
            Box {
                val currentKategoriName = listKategori.find { it.idKategori == selectedKategoriId }?.namaKategori ?: "Pilih Kategori"
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(currentKategoriName)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listKategori.forEach { kategori ->
                        DropdownMenuItem(
                            text = { Text(kategori.namaKategori) },
                            onClick = {
                                selectedKategoriId = kategori.idKategori
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (judul.isNotBlank() && selectedKategoriId != 0) {
                        viewModel.saveBuku(judul, selectedStatus, selectedKategoriId)
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = judul.isNotBlank() && listKategori.isNotEmpty()
            ) {
                Text("Simpan")
            }
        }
    }
}
