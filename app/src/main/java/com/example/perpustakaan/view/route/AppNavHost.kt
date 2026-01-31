package com.example.perpustakaan.view.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.perpustakaan.view.HalamanHome
import com.example.perpustakaan.view.HalamanEntryBuku
import com.example.perpustakaan.view.HalamanKategori
import com.example.perpustakaan.view.HalamanDetailBuku

enum class PerpustakaanScreen {
    Home,
    EntryBuku,
    Kategori,
    DetailBuku
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PerpustakaanScreen.Home.name,
        modifier = modifier
    ) {
        composable(PerpustakaanScreen.Home.name) {
            HalamanHome(
                navigateToEntry = { navController.navigate(PerpustakaanScreen.EntryBuku.name) },
                navigateToKategori = { navController.navigate(PerpustakaanScreen.Kategori.name) },
                onBukuClick = { id -> navController.navigate("${PerpustakaanScreen.DetailBuku.name}/$id") }
            )
        }
        composable(PerpustakaanScreen.EntryBuku.name) {
            HalamanEntryBuku(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(PerpustakaanScreen.Kategori.name) {
            HalamanKategori(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "${PerpustakaanScreen.DetailBuku.name}/{bukuId}",
            arguments = listOf(navArgument("bukuId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bukuId = backStackEntry.arguments?.getInt("bukuId") ?: 0
            HalamanDetailBuku(
                bukuId = bukuId,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
