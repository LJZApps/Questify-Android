package de.ljz.questify.core.data.shared

import androidx.annotation.StringRes
import kotlin.random.Random

sealed class StringWrapper {

    data class Value(val value: String) : StringWrapper() {
        override fun equals(other: Any?) = false

        override fun hashCode() = Random.nextInt()
    }

    data class Resource(@StringRes val resource: Int) : StringWrapper() {
        override fun equals(other: Any?) = false

        override fun hashCode() = Random.nextInt()
    }

    override fun equals(other: Any?) = false

    override fun hashCode() = Random.nextInt()

}