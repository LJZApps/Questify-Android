package de.ljz.questify.util.changelog

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.InputStream

fun parseYamlChangelog(inputStream: InputStream): ChangeLog {
    val mapper = YAMLMapper().apply {
        registerModule(
            KotlinModule.Builder()
                .nullIsSameAsDefault(true) // Oder .configure(...) wie oben gezeigt
                .build()
        )
    }
    return mapper.readValue(inputStream, ChangeLog::class.java)
}