package com.sargis.khlopuzyan.service.ui.component

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sargis.khlopuzyan.service.BlockingService
import com.sargis.khlopuzyan.service.NonBlockingService
import com.sargis.khlopuzyan.service.ui.theme.ServiceTheme

@Composable
fun BlockingNonBlockingServiceScreen(
    modifier: Modifier = Modifier,
    isBlocking: Boolean,
) {

    val context = LocalContext.current

    var count by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var serviceStatus by remember {
            mutableStateOf("Service NOT STARTED")
        }

        Button(
            onClick = {
                count++
            }
        ) {
            Text(
                text = "CLICK TO TEST UI: $count"
            )
        }

        Text(
            text = serviceStatus
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    serviceStatus = "Service STARTED"
//                    val intent = Intent(context, BlockingService::class.java)
//                    val intent = Intent(context, NonBlockingService::class.java)
                    val intent = if (isBlocking)
                        Intent(context, BlockingService::class.java)
                    else
                        Intent(context, NonBlockingService::class.java)
                    context.startService(intent)
                }
            ) {
                Text(
                    text = "START SERVICE"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    serviceStatus = "Service STOPPED"
//                    val intent = Intent(context, BlockingService::class.java)
//                    val intent = Intent(context, NonBlockingService::class.java)
                    val intent = if (isBlocking)
                        Intent(context, BlockingService::class.java)
                    else
                        Intent(context, NonBlockingService::class.java)
                    context.stopService(intent)
                }
            ) {
                Text(
                    text = "STOP SERVICE"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BlockingNonBlockingServiceScreenPreview() {
    ServiceTheme {
        BlockingNonBlockingServiceScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            isBlocking = false
        )
    }
}