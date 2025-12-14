package com.sargis.khlopuzyan.service.ui.component

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
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
import com.sargis.khlopuzyan.service.BoundAndForegroundService
import com.sargis.khlopuzyan.service.MyBoundService
import com.sargis.khlopuzyan.service.ui.theme.ServiceTheme

@Composable
fun BoundAndForegroundServiceScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    var myBoundService = remember {
        MyBoundService()
    }

    var isConnected = remember {
        false
    }

    var systemTime by remember {
        mutableStateOf("System time here")
    }

    val serviceConnection: ServiceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(
                name: ComponentName?,
                service: IBinder,
            ) {
                val binder = service as MyBoundService.MyBinder
                myBoundService = binder.getBoundService()
                isConnected = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isConnected = false
            }
        }
    }

    var count by remember {
        mutableIntStateOf(0)
    }

    var serviceStatus by remember {
        mutableStateOf("Service NOT STARTED")
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                count++
            }
        ) {
            Text(
                text = "CLICK TO TEST UI: $count"
            )
        }

        Button(
            onClick = {
                val intent = Intent(context, MyBoundService::class.java)
                context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        ) {
            Text(
                text = "BIND SERVICE"
            )
        }

        Button(
            onClick = {
                context.unbindService(serviceConnection)
            }
        ) {
            Text(
                text = "UNBIND SERVICE"
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
                    val intent = Intent(context, BoundAndForegroundService::class.java)
                    intent.action = BoundAndForegroundService.Actions.START.toString()
//                    context.startForegroundService(intent)
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
                    val intent = Intent(context, BoundAndForegroundService::class.java)
                    intent.action = BoundAndForegroundService.Actions.STOP.toString()
                    context.stopService(intent)
                }
            ) {
                Text(
                    text = "STOP SERVICE"
                )
            }
        }

        Text(
            text = systemTime
        )

        Button(
            onClick = {
                val time = myBoundService.getSystemTime()
                systemTime = time
            }
        ) {
            Text(
                text = "GET SYSTEM TIME"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoundAndForegroundServiceScreenPreview() {
    ServiceTheme {
        BoundAndForegroundServiceScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}