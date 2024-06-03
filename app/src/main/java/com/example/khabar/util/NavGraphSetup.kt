package com.example.khabar.util

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.khabar.ui.articleScreen.ArticleScreen
import com.example.khabar.ui.newsScreen.NewsScreen
import com.example.khabar.ui.newsScreen.NewsScreenViewModel

private const val argKey = "web_url"

@Composable
fun NavGraphSetup(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = "NEWS_SCREEN"
    ){
        composable(
            route = "NEWS_SCREEN"
        ){
            val viewModel: NewsScreenViewModel = hiltViewModel()
            NewsScreen(
                states = viewModel.state,
                onEvent = viewModel::onEvent,
                onReadFullArticle = {
                    navController.navigate("ARTICLESCREEN?web_url=$it")
                }
            )
        }
        composable(
            route = "ARTICLESCREEN?web_url={web_url}",
            arguments = listOf(navArgument(name = argKey){
                type = NavType.StringType
            })
        ){
            ArticleScreen(
                url = it.arguments?.getString(argKey),
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}