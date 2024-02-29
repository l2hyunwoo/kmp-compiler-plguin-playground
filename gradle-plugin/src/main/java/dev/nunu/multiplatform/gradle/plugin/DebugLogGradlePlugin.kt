package dev.nunu.multiplatform.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DebugLogGradlePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.create("debugLog", DebugLogGradleExtension::class.java)
    }
}

open class DebugLogGradleExtension {
    var enabled: Boolean = false
    var annotations: List<String> = emptyList()
}
