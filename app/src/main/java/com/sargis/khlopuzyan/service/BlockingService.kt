package com.sargis.khlopuzyan.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BlockingService : Service() {

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
        log_e("onStartCommand")
        (0..10).forEach {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            log_e("onStartCommand - while : $it")
        }
//        return super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        log_e("onDestroy")
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        log_e("onTaskRemoved")
//        onDestroy()
        super.onTaskRemoved(rootIntent)
    }
}