package com.g3.learningopengl.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object TextResourceReader {
    private val TAG = "TextResourceReader: "

    fun readTextFileFromResource(context: Context, resourceId: Int): String {
        val body = StringBuilder()
        try {
            val inputStream = context.resources.openRawResource(resourceId)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var line: String?
            do {
                line = bufferedReader.readLine()
                if (line == null)
                    break
                body.append(line)
                body.append('\n')

            } while (true)

        } catch (ex: IOException) {
            throw RuntimeException("Could not open resource: $resourceId")

        } catch (ex1: Resources.NotFoundException) {
            throw RuntimeException("Resource not found: $resourceId")
        }

        return body.toString()
    }

    fun loadStringFromAssetFile(myContext: Context, filePath: String): String? {
        val shaderSource = java.lang.StringBuilder()
        return try {
            val reader = BufferedReader(InputStreamReader(myContext.assets.open(filePath)))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                shaderSource.append(line).append("\n")
            }
            reader.close()
            shaderSource.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "Could not load shader file")
            null
        }
    }

}