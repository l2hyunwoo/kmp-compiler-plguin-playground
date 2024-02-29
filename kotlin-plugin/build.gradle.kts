plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.jetbrainsKotlinKapt)
}

dependencies {
    implementation(libs.kotlin.stdlib)
    compileOnly(libs.kotlin.compiler.embeddable)
    compileOnly(libs.auto.service)
    compileOnly(libs.auto.service.annotations)
    kapt(libs.auto.service)
}
