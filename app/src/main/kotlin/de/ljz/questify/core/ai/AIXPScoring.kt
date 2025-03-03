package de.ljz.questify.core.ai

/*
import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.io.FileInputStream
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AIXPScoring(context: Context) {

    private var interpreter: Interpreter
    private var minXp: Float = 0f
    private var maxXp: Float = 100f
    private var minPoints: Float = 0f
    private var maxPoints: Float = 100f

    init {
        val options = Interpreter.Options()
        options.setUseXNNPACK(true)  // 🚀 Deaktiviert XNNPACK, falls es Probleme macht
        options.setUseNNAPI(false)
        interpreter = Interpreter(loadModelFile(context), options)
        loadScalerParams(context)
    }

    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("quest_xp_model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadScalerParams(context: Context) {
        try {
            val inputStream = context.assets.open("scaler_params.json")
            val jsonStr = inputStream.bufferedReader().use { it.readText() }
            val jsonObj = JSONObject(jsonStr)

            minXp = jsonObj.getJSONObject("xp").getDouble("min").toFloat()
            maxXp = jsonObj.getJSONObject("xp").getDouble("max").toFloat()
            minPoints = jsonObj.getJSONObject("points").getDouble("min").toFloat()
            maxPoints = jsonObj.getJSONObject("points").getDouble("max").toFloat()

            println("✅ Skalierungswerte geladen: XP ($minXp - $maxXp), Points ($minPoints - $maxPoints)")
        } catch (e: Exception) {
            println("⚠️ Fehler beim Laden von scaler_params.json: ${e.message}")
        }
    }

    fun predict(questText: String, difficulty: Float): Pair<Float, Float> {
        // 🚀 **String wird als `String[]` übergeben**
        val textInput = arrayOf(questText)  // Kein ByteBuffer mehr nötig

        // 🚀 Schwierigkeit als FloatBuffer
        val difficultyBuffer = convertFloatToByteBuffer(difficulty)

        // Output-Arrays für XP und Punkte
        val output = Array(2) { FloatArray(1) }

        // Starte die Vorhersage mit TensorFlow Lite
        interpreter.runForMultipleInputsOutputs(arrayOf(textInput, difficultyBuffer), mapOf(0 to output[0], 1 to output[1]))

        // Skaliere die Werte zurück auf den Originalbereich
        val xp = rescaleValue(output[0][0], minXp, maxXp)
        val points = rescaleValue(output[1][0], minPoints, maxPoints)

        return Pair(xp, points)
    }

    private fun convertFloatToByteBuffer(value: Float): ByteBuffer {
        val buffer = ByteBuffer.allocateDirect(4)
        buffer.order(ByteOrder.nativeOrder())
        buffer.putFloat(value)
        buffer.flip()
        return buffer
    }

    private fun rescaleValue(value: Float, min: Float, max: Float): Float {
        return value * (max - min) + min
    }
}*/
