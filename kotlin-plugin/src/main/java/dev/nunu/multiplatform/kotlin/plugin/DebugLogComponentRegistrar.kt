@file:OptIn(ExperimentalCompilerApi::class)

package dev.nunu.multiplatform.kotlin.plugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.jvm.extensions.ClassGeneratorExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(CompilerPluginRegistrar::class)
class DebugLogComponentRegistrar : CompilerPluginRegistrar() {
    override val supportsK2 = false

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        if (configuration[KEY_ENABLED] != true) return
        ClassGeneratorExtension.registerExtension(
            DebugLogClassGenerationInterceptor(configuration[KEY_ANNOTATIONS] ?: emptyList())
        )
    }
}
