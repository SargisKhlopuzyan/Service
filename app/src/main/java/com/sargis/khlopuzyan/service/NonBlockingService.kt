package com.sargis.khlopuzyan.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sargis.khlopuzyan.service.util.log_e

class NonBlockingService : Service() {

    private var stopService = false

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        log_e("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        val runnable = Runnable {
            log_e("onStartCommand")
            (0..10).forEach {
                if (stopService) return@Runnable
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                log_e("onStartCommand - while : $it")
            }
        }

        val thread = Thread(runnable)
        stopService = false
        thread.start()

//        return super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        log_e("onDestroy")
        stopService = true
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        log_e("onTaskRemoved")
//        onDestroy()
        super.onTaskRemoved(rootIntent)
    }
}