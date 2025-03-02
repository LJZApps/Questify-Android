package de.ljz.questify.core.ai

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class FarmingDetector(context: Context) {
    private var interpreter: Interpreter

    init {
        val model = loadModelFile(context, "farming_model.tflite")
        interpreter = Interpreter(model)
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    fun predict(duration: Float, repetitions: Float, difficulty: Float): Boolean {
        val input = arrayOf(floatArrayOf(duration, repetitions, difficulty))
        val output = arrayOf(floatArrayOf(0f))
        interpreter.run(input, output)
        return output[0][0] > 0.5  // Falls >0.5, dann Farming erkannt
    }
}