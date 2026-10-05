package com.hayday.assistant

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.*
import android.widget.TextView

class OverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var view: View? = null

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startForeground(7, notification())
        if (Settings.canDrawOverlays(this)) showOverlay()
    }

    private fun showOverlay() {
        if (view != null) return

        val bubble = TextView(this).apply {
            text = "🌾"
            textSize = 28f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(94, 140, 49))
            setPadding(18, 10, 18, 10)
            setOnClickListener {
                text = if (text == "🌾") "⏱️" else "🌾"
            }
        }

        val type = if (Build.VERSION.SDK_INT >= 26)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 24
            y = 180
        }

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager!!.addView(bubble, params)
        view = bubble
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val c = NotificationChannel(
                "assistant",
                "Hay Day Assistant",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(c)
        }
    }

    private fun notification(): Notification {
        return Notification.Builder(this, "assistant")
            .setContentTitle("Hay Day Assistant")
            .setContentText("المساعد العائم يعمل")
            .setSmallIcon(com.hayday.assistant.R.drawable.ic_launcher)
            .build()
    }

    override fun onDestroy() {
        view?.let { windowManager?.removeView(it) }
        view = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}