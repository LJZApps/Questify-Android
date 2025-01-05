package de.ljz.questify.util.changelog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeLogVersion(
    @SerialName("version")
    val version: Int,

    @SerialName("name")
    val name: String,

    @SerialName("date")
    val date: String,

    @SerialName("features")
    val features: List<String>? = null,

    @SerialName("changes")
    val changes: List<String>? = null,

    @SerialName("bugfixes")
    val bugfixes: List<String>? = null
)

@Serializable
data class ChangeLog(
    @SerialName("versions")
    val versions: List<ChangeLogVersion>
)
