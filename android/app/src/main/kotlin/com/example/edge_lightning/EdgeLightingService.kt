package com.example.edge_lightning

import android.animation.ValueAnimator
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.util.Log

class EdgeLightingService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: View? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        Log.d("EdgeLightingService", "Service created.")
    }

    private fun showOverlay(colorHex: String) {
        if (overlayView != null) return
        Log.d("EdgeLightingService", "Showing overlay with color: $colorHex")

        overlayView = View(this)

        val border = GradientDrawable()
        border.shape = GradientDrawable.RECTANGLE
        border.setStroke(60, android.graphics.Color.parseColor(colorHex))
        border.cornerRadius = 100f

        overlayView?.background = border

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER

        windowManager?.addView(overlayView, params)

        val animator = ValueAnimator.ofInt(50, 200)
        animator.duration = 2000
        animator.repeatMode = ValueAnimator.REVERSE
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val strokeWidth = animation.animatedValue as Int
            border.setStroke(strokeWidth, android.graphics.Color.parseColor(colorHex))
            overlayView?.invalidate()
        }
        animator.start()
    }

    private fun hideOverlay() {
        if (overlayView != null) {
            windowManager?.removeView(overlayView)
            overlayView = null
            Log.d("EdgeLightingService", "Hiding and removing overlay.")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val color = intent?.getStringExtra("color") ?: "#FF00FF"
        showOverlay(color)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        hideOverlay()
        Log.d("EdgeLightingService", "Service destroyed.")
    }
}