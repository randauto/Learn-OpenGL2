package com.g3.learningopengl.utils

import android.opengl.GLES32.*
import android.util.Log


object ShaderHelper {
    val TAG = "ShaderHelper"

    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GL_VERTEX_SHADER, shaderCode)
    }

    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode)
    }

    private fun compileShader(type: Int, shaderCode: String): Int {
        var shaderObjectId = glCreateShader(type)
        if (shaderObjectId == 0) {
            showLog("Could not create new shader.")

            return 0
        }

        var compileStatus = IntArray(1)
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)
        showLog(
            "Results of compiling source:" + "\n" + shaderCode + "\n: ${glGetShaderInfoLog(shaderObjectId)}"
        )

        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            glDeleteShader(shaderObjectId)
            showLog("Compilation of shader failed.")
            return 0
        }
        return shaderObjectId
    }

    fun showLog(msg: String) {
        if (LoggerConfig.ON) {
            Log.d(TAG, msg)
        }
    }

    fun linkProgram(vertexShaderId: Int = 0, fragmentShaderId: Int = 0): Int {
        val programObjectId = glCreateProgram()
        if (programObjectId == 0) {
            showLog("Could not create new program")
            return 0
        }

        // attach vertex va fragment shader into program.
        glAttachShader(programObjectId, vertexShaderId)
        glAttachShader(programObjectId, fragmentShaderId)

        // linking program.
        glLinkProgram(programObjectId)

        val linkStatus = IntArray(1)
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0)

        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId)
            showLog("Linking of program failed.")
            return 0
        }

        return programObjectId
    }

    fun validateProgram(programObjectId: Int): Boolean {
        glValidateProgram(programObjectId)
        val validateStatus = IntArray(1)
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)
        showLog(
            """
     Results of validating program: ${validateStatus[0]}
     Log:${glGetProgramInfoLog(programObjectId)}
     """.trimIndent()
        )
        return validateStatus[0] != 0
    }


}