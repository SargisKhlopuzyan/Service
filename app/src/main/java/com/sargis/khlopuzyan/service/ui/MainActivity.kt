package com.sargis.khlopuzyan.service.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sargis.khlopuzyan.service.ui.component.BlockingNonBlockingServiceScreen
import com.sargis.khlopuzyan.service.ui.component.BoundedServiceScreen
import com.sargis.khlopuzyan.service.ui.theme.ServiceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ServiceTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    val screenMode = ScreenMode.BOUNDED_SERVICE
                    if (screenMode == ScreenMode.BOUNDED_SERVICE) {
                        BoundedServiceScreen(
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
    BOUNDED_SERVICE, BLOCKING_SERVICE, NON_BLOCKING_SERVICE
}