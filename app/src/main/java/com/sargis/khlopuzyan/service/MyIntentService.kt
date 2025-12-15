package com.sargis.khlopuzyan.service

import android.app.IntentService
import android.content.Intent
import com.sargis.khlopuzyan.service.util.log_e

class MyIntentService : IntentService("MyIntentService") {

    init {
        instance = this
    }

    var i = 0

    override fun onHandleIntent(intent: Intent?) {
        log_e("onHandleIntent")

        try {
            isRunning = true
            while (isRunning && i < 5) {
                i++
                log_e("onHandleIntent ... $i")
                Thread.sleep(1000)
            }
        } catch (e: InterruptedException) {

        }

        i = 0
    }

    companion object {
        private lateinit var instance: MyIntentService
        var isRunning = false

        fun stopService() {
            log_e("Service is stopping")
            isRunning = false
            instance.stopSelf()
        }
    }
}