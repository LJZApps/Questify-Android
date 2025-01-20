package de.ljz.questify.util.changelog

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.InputStream

fun parseYamlChangelog(inputStream: InputStream): ChangeLog {
    val mapper = YAMLMapper().apply {
        registerModule(
            KotlinModule.Builder()
                .configure(KotlinFeature.NullIsSameAsDefault, true)
                .build()
        )
    }
    return mapper.readValue(inputStream, ChangeLog::class.java)
}