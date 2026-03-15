// MainActivity.kt
// Demonstrates runtime permission handling in Jetpack Compose.
// Shows three states: not requested, granted, and denied.

package com.kemalcodes.composetutorial

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

// Represents the three possible permission states
enum class PermissionState {
    NOT_REQUESTED,
    GRANTED,
    DENIED
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PermissionDemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Main screen that handles camera permission flow
@Composable
fun PermissionDemoScreen(modifier: Modifier = Modifier) {
    var permissionState by remember { mutableStateOf(PermissionState.NOT_REQUESTED) }

    // Launcher that requests the camera permission and updates state
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionState = if (isGranted) {
            PermissionState.GRANTED
        } else {
            PermissionState.DENIED
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Permission Handling",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Show different content based on the current permission state
        when (permissionState) {
            PermissionState.NOT_REQUESTED -> {
                NotRequestedContent(
                    onRequestPermission = {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                )
            }
            PermissionState.GRANTED -> {
                GrantedContent()
            }
            PermissionState.DENIED -> {
                DeniedContent()
            }
        }
    }
}

// Displayed when the permission has not been requested yet
@Composable
fun NotRequestedContent(onRequestPermission: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This app needs camera access to take photos.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onRequestPermission) {
            Text(text = "Enable Camera")
        }
    }
}

// Displayed when the permission has been granted
@Composable
fun GrantedContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF4CAF50))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Camera Ready",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

// Displayed when the permission has been denied
@Composable
fun DeniedContent() {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Camera permission was denied. The app needs this permission to take photos. You can grant it in the app settings.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Opens the system settings page for this app
        Button(
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = "Open Settings")
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PermissionDemoPreviewLight() {
    AndroidjetpackcomposetutorialTheme(darkTheme = false) {
        PermissionDemoScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun PermissionDemoPreviewDark() {
    AndroidjetpackcomposetutorialTheme(darkTheme = true) {
        PermissionDemoScreen()
    }
}
