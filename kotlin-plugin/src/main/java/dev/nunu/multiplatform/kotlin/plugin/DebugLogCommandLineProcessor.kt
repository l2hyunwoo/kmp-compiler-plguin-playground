@file:OptIn(ExperimentalCompilerApi::class)

package dev.nunu.multiplatform.kotlin.plugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

val KEY_ENABLED = CompilerConfigurationKey<Boolean>("Printer Tag")
val KEY_ANNOTATIONS = CompilerConfigurationKey<List<String>>("Printer Tag")


@AutoService(CommandLineProcessor::class)
class DebugLogCommandLineProcessor : CommandLineProcessor {
    // same as ID from subplugin
    override val pluginId: String = "debuglog"

    // CLI Option
    // enables, debugLogAnnotation
    // 명령줄 옵션
    override val pluginOptions = listOf(
        CliOption(
            optionName = "enabled",
            valueDescription = "<true|false>",
            description = "Whether plugin is enabled"
        ),
        CliOption(
            optionName = "debugLogAnnotation",
            valueDescription = "<fqname>",
            description = "debug-log annotation names",
            required = true,
            allowMultipleOccurrences = true
        )
    )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) = when (option.optionName) {
        "enabled" -> configuration.put(KEY_ENABLED, value.toBoolean())
        "debugLogAnnotation" -> configuration.appendList(KEY_ANNOTATIONS, value)
        else -> error("Unexpected config option ${option.optionName}")
    }
}
