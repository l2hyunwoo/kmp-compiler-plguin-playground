@file:OptIn(ExperimentalCompilerApi::class)

package dev.nunu.multiplatform.compiler.plugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(CompilerPluginRegistrar::class)
class LogCompilerPluginRegistrar: CompilerPluginRegistrar() {
    // TODO supports K2
    override val supportsK2 = false

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val logger = configuration[CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE]
        val loggingTag = requireNotNull(configuration[KEY_TAG]) { "No tag provided for the function printer" }

        IrGenerationExtension.registerExtension(LogIrExtension(logger, loggingTag))
    }
}