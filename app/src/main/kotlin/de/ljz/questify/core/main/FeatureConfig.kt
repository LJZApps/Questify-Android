package de.ljz.questify.core.main

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive

@Serializable
sealed class Feature {
    abstract val key: String
    abstract val title: String
    abstract val description: String
    abstract val activation: List<Activation>

    @Serializable
    @SerialName("your_quests")
    data object YOUR_QUESTS : Feature() {
        override val key: String = "your_quests"
        override val title: String = "Your Quests"
        override val description: String = "All of your Quests"
        override val activation: List<Activation> = listOf(Activation.Initial(true))
    }

    @Serializable
    @SerialName("trophies")
    data object YOUR_TROPHIES : Feature() {
        override val key: String = "trophies"
        override val title: String = "Your Trophies"
        override val description: String = "Trophies you have earned"
        override val activation: List<Activation> = listOf(
            Activation.Initial(false),
            Activation.Condition("trophies", IntValue(1)),
            Activation.Condition("xp", IntValue(1))
        )
    }

    @Serializable
    @SerialName("village_of_ventures")
    data object VILLAGE_OF_VENTURES : Feature() {
        override val key: String = "village_of_ventures"
        override val title: String = "Village of Ventures"
        override val description: String = "A mini-game where users can decorate houses and place NPCs."
        override val activation: List<Activation> = listOf(
            Activation.Initial(false),
            Activation.Condition("level", IntValue(5))
        )
    }

    @Serializable
    @SerialName("health_system")
    data object HEALTH_SYSTEM : Feature() {
        override val key: String = "health_system"
        override val title: String = "Health System"
        override val description: String = "Users earn points for a certain number of steps."
        override val activation: List<Activation> = listOf(
            Activation.Initial(false),
            Activation.Condition("steps", IntValue(1000))
        )
    }

    @Serializable
    @SerialName("discord_server")
    data object DISCORD_SERVER : Feature() {
        override val key: String = "discord_server"
        override val title: String = "Discord Server"
        override val description: String = "A server for support, ideas, and discussions about the app."
        override val activation: List<Activation> = listOf(
            Activation.Initial(false),
            Activation.Condition("click", StringValue("community_link"))
        )
    }

    @Serializable
    @SerialName("dashboard")
    data object DASHBOARD : Feature() {
        override val key: String = "dashboard"
        override val title: String = "Dashboard"
        override val description: String = "Displays an overview of quests and routines."
        override val activation: List<Activation> = listOf(
            Activation.Initial(false),
            Activation.Condition("active_quests", IntValue(1))
        )
    }

    companion object {
        private val features = listOf(
            YOUR_QUESTS, YOUR_TROPHIES, VILLAGE_OF_VENTURES,
            HEALTH_SYSTEM, DISCORD_SERVER, DASHBOARD
        )

        fun fromKey(key: String): Feature? = features.find { it.key == key }

        // Alternative lookup approach with better performance for many features
        /*
        private val featureMap = features.associateBy { it.key }
        fun fromKey(key: String): Feature? = featureMap[key]
        */
    }
}

@Serializable
sealed class Activation {
    @Serializable
    @SerialName("initial")
    data class Initial(val initial: Boolean) : Activation()

    @Serializable
    @SerialName("condition")
    data class Condition(val key: String, val value: ConditionValue) : Activation()
}

@Serializable(with = ConditionValueSerializer::class)
sealed interface ConditionValue {
    val value: Any
}

@Serializable
@SerialName("int")
data class IntValue(override val value: Int) : ConditionValue

@Serializable
@SerialName("string")
data class StringValue(override val value: String) : ConditionValue

object ConditionValueSerializer : JsonContentPolymorphicSerializer<ConditionValue>(ConditionValue::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out ConditionValue> {
        return when {
            element.jsonPrimitive.isString -> StringValue.serializer()
            else -> IntValue.serializer()
        }
    }
}

// Alternativ: Einen expliziten StringValue oder IntValue in JSON als {"type": "string", "value": "..."}
/*
@Serializable
sealed class ConditionValue {
    abstract val value: Any

    @Serializable
    @SerialName("int")
    data class IntValue(override val value: Int) : ConditionValue()

    @Serializable
    @SerialName("string")
    data class StringValue(override val value: String) : ConditionValue()
}
*/