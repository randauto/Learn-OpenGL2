package com.g3.practiceopengl.opengl

import android.content.Context
import android.opengl.GLES20
import com.g3.learningopengl.R
import com.g3.learningopengl.utils.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

const val COORDS_PER_VERTEX = 3
var triangleCoords = floatArrayOf(
    // in counterclockwise order:
    0.0f, 0.5f, 0.0f,      // top left
    -0.5f, -0.5f, 0.0f,    // bottom left
    0.5f, -0.5f, 0.0f      // bottom right
//    0.5f, 0.5f, 0.0f,      // top right
)

class Triange(context: Context) {
    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(0.5f, 0.6f, 0.7f, 1.0f)

    private var mProgram: Int

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0

    private val vertexShaderCode =
        "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}"

    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"

    init {
        var vertexShaderSource =
            context?.let {
                TextResourceReader.readTextFileFromResource(
                    it,
                    R.raw.simple_vertex_shader
                )
            }

        var fragmentShaderSource =
            context?.let {
                TextResourceReader.readTextFileFromResource(
                    it,
                    R.raw.simple_fragment_shader
                )
            }

        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderSource)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource)

        mProgram = GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }


    }

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    fun draw(vPMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )
            // get handle to shape's transformation matrix
            vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vPMatrix, 0)

            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also {
                GLES20.glUniform4fv(mColorHandle, 1, color, 0)
            }

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
            GLES20.glDisableVertexAttribArray(it)

        }
    }

    fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }

    }

    private var vertexBuffer: FloatBuffer =
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())
            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(triangleCoords)
                // set the buffer to read the first coordinate
                position(0)
            }
        }
}