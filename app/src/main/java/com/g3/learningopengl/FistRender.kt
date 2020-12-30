package com.g3.learningopengl

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.g3.learningopengl.utils.LoggerConfig
import com.g3.learningopengl.utils.ShaderHelper
import com.g3.learningopengl.utils.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class FistRender : GLSurfaceView.Renderer {
    private val POSITION_COMPONENT_COUNT = 2

    private val BYTES_PER_FLOAT = 4

    private var vertexData: FloatBuffer? = null

    private var mContext: Context? = null

    private var program: Int = 0

    private var U_COLOR = "u_Color"
    private var uColorLocation = 0

    private var A_POSITION = "a_Position"
    private var aPositionLocation = 0

    constructor(context: Context) {
        this.mContext = context
    }


    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        Log.d("TTTT", "onSurfaceCreated")
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

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

        // khởi tạo vùng bộ nhớ cho mảng float chứa tọa độ các điểm
        vertexData = ByteBuffer
            .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()

        // sao chép vùng nhớ mảng từ Java David xuống bộ nhớ của native, nơi mà OPenGL có thể truy cập trực tiếp.
        vertexData!!.put(tableVerticesWithTriangles)
        vertexData?.position(0)

        var vertexShaderSource =
            mContext?.let {
                TextResourceReader.readTextFileFromResource(
                    it,
                    R.raw.simple_vertex_shader
                )
            }

        var fragmentShaderSource =
            mContext?.let {
                TextResourceReader.readTextFileFromResource(
                    it,
                    R.raw.simple_fragment_shader
                )
            }


        val vertexShader = vertexShaderSource?.let { ShaderHelper.compileVertexShader(it) }
        val fragmentShader = fragmentShaderSource?.let { ShaderHelper.compileFragmentShader(it) }


        if (vertexShader != null && vertexShader != 0) {
            if (fragmentShader != null && fragmentShader != 0) {
                program = ShaderHelper.linkProgram(vertexShader, fragmentShader)
                if (LoggerConfig.ON
                ) {
                    ShaderHelper.validateProgram(program)
                }

                glUseProgram(program)
                uColorLocation = glGetUniformLocation(program, U_COLOR)
                aPositionLocation = glGetAttribLocation(program, A_POSITION)

            }
        }


    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        // đưa tầm nhìn về 0,0 và có rộng dài
        Log.d("TTTT", "onSurfaceChanged")
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(p0: GL10?) {
        glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GL_FLOAT,
            false,
            0,
            vertexData
        )

        glEnableVertexAttribArray(aPositionLocation)
        Log.d("TTTT", "onDrawFrame")
        // xóa màu đi, để màu định nghĩa ở phần created.
        glClear(GL_COLOR_BUFFER_BIT)
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 6)

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_LINES, 6, 2)
        // Draw the first mallet blue.
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        glDrawArrays(GL_POINTS, 8, 1)
        // Draw the second mallet red.
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        glDrawArrays(GL_POINTS, 9, 1)
    }

}
