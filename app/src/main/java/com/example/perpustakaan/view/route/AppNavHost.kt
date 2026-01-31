package com.example.perpustakaan.view.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perpustakaan.view.HalamanDetailBuku
import com.example.perpustakaan.view.HalamanEntryBuku
import com.example.perpustakaan.view.HalamanHome
import com.example.perpustakaan.view.HalamanKategori

object Routes {
    const val HOME = "home"
    const val ENTRY_BUKU = "entry_buku"
    const val KATEGORI = "kategori"
    const val DETAIL_BUKU = "detail_buku"
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HalamanHome(
                navigateToEntry = { navController.navigate(Routes.ENTRY_BUKU) },
                navigateToKategori = { navController.navigate(Routes.KATEGORI) },
                onBukuClick = { id -> navController.navigate("${Routes.DETAIL_BUKU}/$id") }
            )
        }
        composable(Routes.ENTRY_BUKU) {
            HalamanEntryBuku(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.KATEGORI) {
            HalamanKategori(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "${Routes.DETAIL_BUKU}/{bukuId}",
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
