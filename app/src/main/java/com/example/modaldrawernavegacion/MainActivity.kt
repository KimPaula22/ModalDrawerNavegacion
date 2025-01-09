package com.example.modaldrawernavegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.modaldrawernavegacion.ui.theme.ModalDrawerNavegacionTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModalDrawerNavegacionTheme {
                AppWithModalDrawer()
            }
        }
    }
}

@Composable
fun AppWithModalDrawer() {
    val navController = rememberNavController() // Recordatorio de estado para NavController
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController, closeDrawer = {
                coroutineScope.launch { drawerState.close() }
            })
        }
    ) {
        MainContent(navController = navController, openDrawer = {
            coroutineScope.launch { drawerState.open() }
        })
    }
}

@Composable
fun DrawerContent(navController: NavHostController, closeDrawer: () -> Unit) {
    // Contenido del Drawer (menú de navegación)
    Column(modifier = Modifier.padding(16.dp)) {
        TextButton(onClick = {
            navController.navigate("main_screen") // Navegamos a la pantalla principal
            closeDrawer()
        }) {
            Text(text = "Ir a la Pantalla Principal")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = {
            navController.navigate("settings_screen") // Navegamos a la pantalla de configuración
            closeDrawer()
        }) {
            Text(text = "Ir a Configuración")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(navController: NavHostController, openDrawer: () -> Unit) {
    // Contenido principal con la TopAppBar y la navegación entre pantallas
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Aplicación con ModalDrawer") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Navegación entre pantallas
        NavHost(
            navController = navController,
            startDestination = "main_screen", // Este es un String, no KClass
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("main_screen") { MainScreen() } // Pantalla Principal
            composable("settings_screen") { SettingsScreen() } // Pantalla de configuración
        }
    }
}

@Composable
fun MainScreen() {
    // Pantalla principal con imagen de fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.homescreen), // Cambia esto por tu imagen
            contentDescription = "Fondo principal",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = "Bienvenido a la aplicación",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SettingsScreen() {
    // Pantalla de configuración con imagen de fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.homescreen), // Cambia esto por tu imagen
            contentDescription = "Fondo configuración",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = "Configuración de la App",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ModalDrawerNavegacionTheme {
        AppWithModalDrawer()
    }
}
