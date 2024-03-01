package dev.nunu.multiplatform.kotlin.plugin

import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.Type
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter
import java.lang.StringBuilder

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
                    // method-trace-printing code
                    invokestatic("j/l/System", "currentTimeMillis", "()J", false)
                    // store-random-strat-time
                    store(9001, Type.LONG_TYPE)
                    getstatic("j/l/System", "out", "Ljava/io/PrintStream;")
                    anew(Type.getType(StringBuilder::class.java))
                    invokespecial(
                        "j/l/StringBuilder",
                        "<init>",
                        "()V",
                        false
                    )
                    visitLdcInsn("-> ${function.name}(")
                    invokevirtual(
                        "j/l/StringBuilder",
                        "append",
                        "(Lj/l/String;)Lj/l/SB;",
                        false
                    )
                    function.valueParameters.forEachIndexed { index, valueParameterDescriptor ->
                        visitLdcInsn("${valueParameterDescriptor.name}=")
                        invokevirtual(
                            "j/l/StringBuilder",
                            "append",
                            "(Lj/l/String;)Lj/l/SB;",
                            false
                        )
                        visitVarInsn(Opcodes.ALOAD, index + 1)
                        invokevirtual(
                            "j/l/StringBuilder",
                            "append",
                            "(Lj/l/String;)Lj/l/SB;",
                            false
                        )
                    }
                    invokevirtual(
                        "j/l/StringBuilder",
                        "toString",
                        "(Lj/l/String;)Lj/l/String;",
                        false
                    )
                    invokevirtual(
                        "j/l/PrintStream",
                        "println",
                        "(Lj/l/String;)V",
                        false
                    )
                }
            }

            override fun visitInsn(opcode: Int) {
                when (opcode) {
                    // all of the opcodes that result in a return
                    Opcodes.RETURN, // void return
                    Opcodes.ARETURN, // object return
                    Opcodes.IRETURN, Opcodes.FRETURN, Opcodes.LRETURN, Opcodes.DRETURN // int, float, long, double return
                    -> {
                        InstructionAdapter(this).apply {
                            anew(Type.getType(StringBuilder::class.java))
                            dup()
                            invokespecial(
                                "j/l/StringBuilder",
                                "<init>",
                                "()V",
                                false
                            )
                            visitLdcInsn("<- ${function.name} [ ran in")
                            invokevirtual(
                                "j/l/StringBuilder",
                                "append",
                                "(Lj/l/String;)Lj/l/StringBuilder;",
                                false
                            )
                            invokestatic("j/l/System", "currentTimeMillis", "()J", false)
                            load(9001, Type.LONG_TYPE)
                            sub(Type.LONG_TYPE)
                            invokevirtual(
                                "j/l/StringBuilder",
                                "append",
                                "(J)Lj/l/StringBuilder;",
                                false
                            )
                            visitLdcInsn("ms ]")
                            invokevirtual(
                                "j/l/StringBuilder",
                                "append",
                                "(Lj/l/String;)Lj/l/StringBuilder;",
                                false
                            )
                            invokevirtual(
                                "j/l/StringBuilder",
                                "toString",
                                "(Lj/l/String;)Lj/l/String;",
                                false
                            )
                        }
                    }
                }
                super.visitInsn(opcode)
            }
        }
    }

    override fun getDelegate() = delegateBuilder
}