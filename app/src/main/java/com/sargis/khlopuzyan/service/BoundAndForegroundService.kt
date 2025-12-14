package com.sargis.khlopuzyan.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.sargis.khlopuzyan.service.util.log_e

class BoundAndForegroundService : Service() {

    val iBinder = MyBinder()

    inner class MyBinder : Binder() {
        val myBinder = this@BoundAndForegroundService
    }

    override fun onBind(intent: Intent): IBinder {
        log_e("onBind")
        return iBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        log_e("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        log_e("onCreate")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        log_e("onStart")
        super.onStart(intent, startId)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }

        log_e("onStartCommand")
//        return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    private fun start() {
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        log_e("onTaskRemoved")
        onDestroy()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        log_e("onDestroy")
        super.onDestroy()
    }

    private fun createNotification(): Notification {
        val builder = NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Bound And Foreground Service")
            .setContentText("Running in the foreground")
        return builder.build()
    }

    enum class Actions {
        START, STOP
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "running_channel"
        const val CHANNEL_NAME = "BoundAndForegroundServiceChannel"
    }
}