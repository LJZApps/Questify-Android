package de.ljz.questify.core.ai

import android.content.Context
import org.json.JSONObject
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class AIXPScoring(context: Context) {
    private var interpreter: Interpreter
    private val xpScaler = MinMaxScaler()
    private val pointsScaler = MinMaxScaler()

    init {
        // TFLite Modell laden
        val model = loadModelFile(context, "quest_xp_model.tflite")

        // TensorFlow Lite Optionen konfigurieren
        val options = Interpreter.Options()
        options.setNumThreads(4) // Anzahl der CPU-Threads

        interpreter = Interpreter(model, options)

        // Skaler-Parameter laden
        loadScalerParameters(context)
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            fileDescriptor.startOffset,
            fileDescriptor.declaredLength
        )
    }

    private fun loadScalerParameters(context: Context) {
        try {
            // JSON-Datei aus Assets laden
            val jsonString = context.assets.open("scaler_params.json").bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            // XP-Skaler Parameter
            val xpParams = jsonObject.getJSONObject("xp")
            xpScaler.setParameters(
                xpParams.getDouble("min").toFloat(),
                xpParams.getDouble("max").toFloat()
            )

            // Points-Skaler Parameter
            val pointsParams = jsonObject.getJSONObject("points")
            pointsScaler.setParameters(
                pointsParams.getDouble("min").toFloat(),
                pointsParams.getDouble("max").toFloat()
            )
        } catch (e: Exception) {
            // Fallback zu Standard-Werten
            xpScaler.setParameters(0f, 100f)
            pointsScaler.setParameters(0f, 100f)
            e.printStackTrace()
        }
    }

    fun predictXPAndPoints(questText: String, difficulty: Float): Pair<Int, Int> {
        try {
            // Für TensorFlow Lite Inputs vorbereiten
            val inputs = arrayOf<Any>(
                arrayOf(questText), // quest_text - Ein String-Array
                floatArrayOf(difficulty) // difficulty - Ein Float-Array
            )

            // Outputs vorbereiten
            val outputXp = Array(1) { FloatArray(1) } // Ein 1x1 Float-Array für XP
            val outputPoints = Array(1) { FloatArray(1) } // Ein 1x1 Float-Array für Points
            val outputs = mapOf(
                0 to outputXp,
                1 to outputPoints
            )

            // Vorhersage ausführen
            interpreter.runForMultipleInputsOutputs(inputs, outputs)

            // Werte zurückskalieren
            val scaledXP = xpScaler.inverseTransform(outputXp[0][0])
            val scaledPoints = pointsScaler.inverseTransform(outputPoints[0][0])

            return Pair(scaledXP.toInt(), scaledPoints.toInt())
        } catch (e: Exception) {
            e.printStackTrace()

            // Fallback: Einfache regelbasierte Berechnung
            return calculateFallbackValues(questText, difficulty)
        }
    }

    // Einfache regelbasierte Berechnung als Fallback
    private fun calculateFallbackValues(questText: String, difficulty: Float): Pair<Int, Int> {
        val baseXP = when {
            difficulty <= 0.3f -> 10
            difficulty <= 0.6f -> 20
            else -> 30
        }

        val basePoints = (baseXP * 1.5f).toInt()

        // Textlänge beeinflusst den Wert
        val lengthMultiplier = (1.0f + questText.length / 100.0f).coerceAtMost(2.0f)

        return Pair(
            (baseXP * lengthMultiplier).toInt(),
            (basePoints * lengthMultiplier).toInt()
        )
    }

    // MinMaxScaler für die Rücktransformation
    inner class MinMaxScaler {
        private var min: Float = 0f
        private var max: Float = 100f

        fun setParameters(min: Float, max: Float) {
            this.min = min
            this.max = max
        }

        fun inverseTransform(normalizedValue: Float): Float {
            return normalizedValue * (max - min) + min
        }
    }
}