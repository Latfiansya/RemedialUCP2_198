package com.example.perpustakaan.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpustakaan.viewmodel.DetailBukuViewModel
import com.example.perpustakaan.viewmodel.provider.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetailBuku(
    bukuId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailBukuViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val buku by viewModel.buku.collectAsState()

    LaunchedEffect(bukuId) {
        viewModel.getBukuById(bukuId)
    }

    Scaffold(
        topBar = {
            PerpustakaanTopAppBar(
                title = "Detail Buku",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (buku != null) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = buku!!.judul, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Status: ${buku!!.status}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Kategori ID: ${buku!!.kategoriId}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Is Deleted: ${buku!!.isDeleted}", style = MaterialTheme.typography.bodySmall)
                    }
                }
                
                // Remedial Requirement Check: "If Book not borrowed -> Delete Dynamic"
                // But Prompt says "Logic Deletion" is on CATEGORY deletion.
                // For Book, we usually just have standard delete or nothing.
                // Assuming simple view for now as requested.
            } else {
                Text("Memuat data buku...")
            }
        }
    }
}
