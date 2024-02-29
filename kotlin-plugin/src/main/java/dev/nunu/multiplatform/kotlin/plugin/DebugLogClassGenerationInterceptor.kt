package dev.nunu.multiplatform.kotlin.plugin

import org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
import org.jetbrains.kotlin.backend.jvm.extensions.ClassGeneratorExtension
import org.jetbrains.kotlin.ir.declarations.IrClass

class DebugLogClassGenerationInterceptor(
    private val debugLogAnnotations: List<String>
) : ClassGeneratorExtension {
    override fun generateClass(
        generator: ClassGenerator,
        declaration: IrClass?
    ): ClassGenerator = object: ClassGenerator by generator {

    }
}
