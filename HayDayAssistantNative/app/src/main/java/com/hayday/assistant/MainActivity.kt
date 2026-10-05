package com.hayday.assistant

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = TextView(this).apply {
            text = "🌾 Hay Day Assistant"
            textSize = 26f
            setPadding(32, 48, 32, 24)
        }

        val info = TextView(this).apply {
            text = "مساعد عائم للمؤقتات والمخزون والاقتراحات. لا يتحكم تلقائياً داخل Hay Day."
            textSize = 16f
            setPadding(32, 8, 32, 32)
        }

        val overlay = Button(this).apply {
            text = "السماح بالظهور فوق التطبيقات"
            setOnClickListener {
                startActivity(Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ))
            }
        }

        val start = Button(this).apply {
            text = "تشغيل المساعد العائم"
            setOnClickListener {
                if (Build.VERSION.SDK_INT >= 33 &&
                    checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS), 10
                    )
                }
                startService(Intent(this@MainActivity, OverlayService::class.java))
            }
        }

        val stop = Button(this).apply {
            text = "إيقاف المساعد"
            setOnClickListener {
                stopService(Intent(this@MainActivity, OverlayService::class.java))
            }
        }

        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(title)
            addView(info)
            addView(overlay)
            addView(start)
            addView(stop)
        })
    }
}