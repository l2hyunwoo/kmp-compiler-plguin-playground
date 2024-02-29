plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.kotlin.compiler.embeddable)
    compileOnly(libs.auto.service.annotations)
    kapt(libs.auto.service)
}