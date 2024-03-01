package dev.nunu.multiplatform.compiler

class Greeting {
    private val platform: Platform = getPlatform()

    // @DebugLog
    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}