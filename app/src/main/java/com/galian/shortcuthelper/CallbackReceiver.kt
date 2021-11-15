package com.galian.shortcuthelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CallbackReceiver : BroadcastReceiver() {
    private val TAG = "CallbackReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Logger.d(TAG, intent.toString())
        if (MainActivity.CALLBACK_ACTION.equals(intent.action)) {
        }
    }
}