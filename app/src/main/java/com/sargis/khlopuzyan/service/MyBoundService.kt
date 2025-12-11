package com.sargis.khlopuzyan.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyBoundService : Service() {

    inner class MyBinder: Binder() {
        fun getBoundService(): MyBoundService {
            return this@MyBoundService
        }
    }

    // The same
//    inner class MyBinder : Binder() {
//        val boundService = this@MyBoundService
//    }

    val binder: MyBinder = MyBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    fun getSystemTime(): String {
        val systemTime = SimpleDateFormat("hh:mm:ss dd/mm/yyyy", Locale.ENGLISH)
        return systemTime.format(Date())
    }
}