package com.g3.practiceopengl.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGlRenderer() : GLSurfaceView.Renderer {
    var context: Context? = null
    constructor(context: Context) : this() {
        this.context = context
    }

    private lateinit var mTriange: Triange

    private lateinit var mSquare: Square2


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // initialize a triangle
        mTriange = Triange(context!!)
        // initialize a square
//        mSquare = Square2()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
    }

    override fun onDrawFrame(gl: GL10?) {
        mTriange.draw()
    }
}