@file:OptIn(ExperimentalCompilerApi::class)

package dev.nunu.multiplatform.compiler.plugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

const val PluginId = "dev.nunu.multiplatform.printer"

val KEY_TAG = CompilerConfigurationKey<String>("Printer Tag")
val OPTION_TAG = CliOption(
    optionName = "printer",
    valueDescription = "Function printer",
    description = KEY_TAG.toString()
)

@AutoService(CommandLineProcessor::class)
class LogCommandLineProcessor : CommandLineProcessor {
    override val pluginId = PluginId
    override val pluginOptions = listOf(OPTION_TAG)
    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        when (val name = option.optionName) {
            OPTION_TAG.optionName -> configuration.put(KEY_TAG, value)
            else -> error("Unexpected config option $name")
        }
    }

}