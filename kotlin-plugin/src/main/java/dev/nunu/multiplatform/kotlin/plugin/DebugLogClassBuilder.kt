package dev.nunu.multiplatform.kotlin.plugin

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter

class DebugLogClassBuilder(
    private val annotations: List<String>,
    private val delegateBuilder: ClassBuilder
) : DelegatingClassBuilder() {
    override fun newMethod(
        origin: JvmDeclarationOrigin,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val original = super.newMethod(origin, access, name, desc, signature, exceptions)
        val function = origin.descriptor as? FunctionDescriptor ?: return original
        if (annotations.none { function.annotations.hasAnnotation(FqName(it)) }) {
            return original
        }
        return object : MethodVisitor(Opcodes.ASM5, original) {
            override fun visitCode() {
                super.visitCode()
                InstructionAdapter(this).apply {
                    TODO("on method entry")
                }
            }

            override fun visitInsn(opcode: Int) {
                when (opcode) {
                    Opcodes.RETURN -> {
                        InstructionAdapter(this).apply {
                            TODO("on method exit")
                        }
                    }
                }
                super.visitInsn(opcode)
            }
        }
    }

    override fun getDelegate() = delegateBuilder
}