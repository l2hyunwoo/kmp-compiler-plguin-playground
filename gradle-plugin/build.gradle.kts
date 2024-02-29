plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.jetbrainsKotlinKapt)
}

gradlePlugin {
    plugins {
        create("debuglog-plugin") {
            id = "dev.nunu.multiplatform.debuglog.plugin"
            implementationClass = "dev.nunu.multiplatform.gradle.plugin.DebugLogGradlePlugin"
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.gradle.api)
    compileOnly(libs.auto.service.annotations)
    compileOnly(libs.auto.service)
    kapt(libs.auto.service)
}