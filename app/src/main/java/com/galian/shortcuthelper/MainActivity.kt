package com.galian.shortcuthelper

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.galian.shortcuthelper.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var mBinding: ActivityMainBinding

    companion object {
        const val CALLBACK_ACTION = "com.galian.shortcuthelper.action.CALLBACK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initViews()
    }

    private fun initViews() {
        val strBuilder = StringBuilder()
        strBuilder.append("Create shortcut for com.android.documentsui.files.FilesActivity")
            .append(" in package com.google.android.documentsui")
        mBinding.textViewDescFilesActivity.text = strBuilder.toString()
        mBinding.buttonCreateFilesActivityShortcut.setOnClickListener { createShortcutFilesActivity() }
    }

    private fun createShortcutFilesActivity() {
        Logger.d(TAG, "createShortcutFilesActivity()")
        val supported = ShortcutManagerCompat.isRequestPinShortcutSupported(this)
        if (!supported) {
            Toast.makeText(this, "Can not create shortcut!", Toast.LENGTH_LONG).show()
            return
        }

        val component = ComponentName(
            "com.google.android.documentsui",
            "com.android.documentsui.files.FilesActivity"
        )
        val shortcutIntent = Intent()
        shortcutIntent.component = component
        shortcutIntent.action = "android.intent.action.MAIN"
        shortcutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        try {
            val activityInfo = packageManager.getActivityInfo(component, PackageManager.MATCH_ALL)
            Logger.d(TAG, activityInfo.toString())
        } catch (e: Exception) {
            Toast.makeText(this, "FilesActivity not exists.", Toast.LENGTH_LONG).show()
            return
        }

        val shortcut = ShortcutInfoCompat.Builder(this, "id1")
            .setShortLabel("FilesActivity")
            .setLongLabel("Open FilesActivity")
            .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher))
            .setIntent(shortcutIntent)
            .build()

        ShortcutManagerCompat.requestPinShortcut(this, shortcut, null)
    }

    private fun createShortcutFilesActivityCallback() {
        Logger.d(TAG, "createShortcutFilesActivity()")
        val supported = ShortcutManagerCompat.isRequestPinShortcutSupported(this)
        if (!supported) {
            Toast.makeText(this, "Can not create shortcut!", Toast.LENGTH_LONG).show();
            return
        }

        val shortcutIntent = Intent("android.intent.action.MAIN")
        shortcutIntent.setClassName(
            "com.google.android.documentsui",
            "com.android.documentsui.files.FilesActivity"
        )
        shortcutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val shortcut = ShortcutInfoCompat.Builder(this, "id1")
            .setShortLabel("FilesActivity")
            .setLongLabel("Open FilesActivity")
            .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher))
            .setIntent(shortcutIntent)
            .build()

        val callbackIntent = Intent(this, CallbackReceiver::class.java)
        callbackIntent.action = CALLBACK_ACTION
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, callbackIntent, 0
        )
        val callback = pendingIntent.intentSender
        ShortcutManagerCompat.requestPinShortcut(this, shortcut, callback)
    }
}