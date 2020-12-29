package com.g3.learningopengl

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FistRender : GLSurfaceView.Renderer {
    private val POSITION_COMPONENT_COUNT = 2

    private val BYTES_PER_FLOAT = 4

    private var vertexData: FloatBuffer? = null

    constructor() {
        val tableVerticesWithTriangles = floatArrayOf(
                // Triangle 1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                // Triangle 2
                0f, 0f,
                9f, 0f,
                9f, 14f,

                // Line 1
                0f, 7f,
                9f, 7f,
                // Mallets
                4.5f, 2f,
                4.5f, 12f
        )

        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()

        vertexData!!.put(tableVerticesWithTriangles);
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        Log.d("TTTT", "onSurfaceCreated")
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        Log.d("TTTT", "onSurfaceChanged")
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(p0: GL10?) {
        Log.d("TTTT", "onDrawFrame")
        glClear(GL_COLOR_BUFFER_BIT)
    }

}
