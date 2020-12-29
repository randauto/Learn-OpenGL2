package com.g3.learningopengl

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var glSurfaceView : GLSurfaceView? = null
    private var isRender = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityManager : ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                        && (Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.startsWith("unknown")
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")))

        glSurfaceView = GLSurfaceView(this)

        if (supportsEs2) {
            glSurfaceView?.let {
                with(glSurfaceView!!) {
                    setEGLContextClientVersion(2)
                    setRenderer(FistRender())
                    renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
                }
            }

            isRender = true

        } else {
            Toast.makeText(this@MainActivity, "Not support OPENGL", Toast.LENGTH_SHORT).show()
        }

        setContentView(glSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        if (isRender) {
            glSurfaceView?.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isRender) {
            glSurfaceView?.onPause()
        }
    }
}