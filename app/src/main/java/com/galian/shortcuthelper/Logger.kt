package com.galian.shortcuthelper

import android.util.Log

class Logger {
    companion object {
        const val TAG = "ShortcutHelper"
        fun d(subTag: String, msg: String) {
            Log.d("$TAG", "[$subTag] $msg")
        }
    }
}