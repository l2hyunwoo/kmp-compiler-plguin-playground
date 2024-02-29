package dev.nunu.multiplatform.gradle.plugin

import com.google.auto.service.AutoService
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(KotlinCompilerPluginSupportPlugin::class)
class DebugLogGradleSubPlugin : KotlinCompilerPluginSupportPlugin {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = with(kotlinCompilation) {
        target.project.plugins.hasPlugin(DebugLogGradlePlugin::class.java)
    }

    override fun getCompilerPluginId() = "debuglog"

    override fun getPluginArtifact() = SubpluginArtifact(
        groupId = "dev.nunu.multiplatform.gradle.plugin",
        artifactId = "debuglog",
        version = "0.0.1"
    )

    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> = with(kotlinCompilation.target.project) {
        val extension = extensions.findByType(DebugLogGradleExtension::class.java) ?: DebugLogGradleExtension()
        if (extension.enabled && extension.annotations.isEmpty()) {
            error("DebugLog is enabled but no annotations are provided.")
        }
        val annotationOptions = extension.annotations.map {
            SubpluginOption(key = "debugLogAnnotation", value = it)
        }
        val enabledOption = SubpluginOption(key = "enabled", value = extension.enabled.toString())
        provider { annotationOptions + enabledOption }
    }
}
