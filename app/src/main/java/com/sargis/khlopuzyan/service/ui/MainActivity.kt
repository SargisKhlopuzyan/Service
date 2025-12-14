package com.sargis.khlopuzyan.service.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.sargis.khlopuzyan.service.ui.component.BlockingNonBlockingServiceScreen
import com.sargis.khlopuzyan.service.ui.component.BoundAndForegroundServiceScreen
import com.sargis.khlopuzyan.service.ui.component.BoundServiceScreen
import com.sargis.khlopuzyan.service.ui.theme.ServiceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        enableEdgeToEdge()
        setContent {
            ServiceTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    val screenMode = ScreenMode.BOUND_AND_FOREGROUND_SERVICE
                    if (screenMode == ScreenMode.BOUND_SERVICE) {
                        BoundServiceScreen(
                            modifier = Modifier.Companion
                                .fillMaxSize()
                                .padding(16.dp)
                                .padding(innerPadding)
                        )
                    } else if (screenMode == ScreenMode.BOUND_AND_FOREGROUND_SERVICE) {
                        BoundAndForegroundServiceScreen(
                            modifier = Modifier.Companion
                                .fillMaxSize()
                                .padding(16.dp)
                                .padding(innerPadding)
                        )
                    } else {
                        BlockingNonBlockingServiceScreen(
                            modifier = Modifier.Companion
                                .fillMaxSize()
                                .padding(16.dp)
                                .padding(innerPadding),
                            isBlocking = screenMode == ScreenMode.BLOCKING_SERVICE
                        )
                    }
                }
            }
        }
    }
}

enum class ScreenMode {
    BOUND_SERVICE,
    BOUND_AND_FOREGROUND_SERVICE,
    BLOCKING_SERVICE,
    NON_BLOCKING_SERVICE
}