package dev.nunu.multiplatform.compiler

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform