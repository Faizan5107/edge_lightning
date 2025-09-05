package com.example.edge_lightning

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.content.Intent

class MainActivity: FlutterActivity() {
    private val CHANNEL = "edge_lighting"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "showEdgeLighting") {
                val color = call.argument<String>("color")
                val intent = Intent(this, EdgeLightingService::class.java).apply {
                    putExtra("color", color)
                }
                startService(intent)
                result.success(null)
            } else {1
                result.notImplemented()
            }
        }
    }
}