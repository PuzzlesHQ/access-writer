package dev.puzzleshq.accesswriter.transformers;

import dev.puzzleshq.accesswriter.AccessWriters;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AccessTransformer extends ClassVisitor {

    String className;

    public AccessTransformer(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        access = AccessWriters.MERGED.get(name).apply(access);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        access = AccessWriters.MERGED.get(name).apply(access);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        access = AccessWriters.MERGED.get(className, name, descriptor).apply(access);
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        access = AccessWriters.MERGED.get(className, name).apply(access);
        return super.visitField(access, name, descriptor, signature, value);
    }
}
