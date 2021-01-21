package com.g3.practiceopengl.opengl

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGlRenderer : GLSurfaceView.Renderer {

    private lateinit var mTriange: Triange

    private lateinit var mSquare: Square2


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // initialize a triangle
        mTriange = Triange()
        // initialize a square
//        mSquare = Square2()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
    }

    override fun onDrawFrame(gl: GL10?) {
        mTriange.draw()
    }
}