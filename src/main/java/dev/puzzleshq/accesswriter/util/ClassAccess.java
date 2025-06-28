package dev.puzzleshq.accesswriter.util;

import dev.puzzleshq.annotation.documentation.NeedsDocumentation;
import dev.puzzleshq.annotation.stability.Stable;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.AccessFlag;
import java.util.Objects;
import java.util.Set;

@Stable
@NeedsDocumentation
public final class ClassAccess {

    public static final ClassAccess ACC_DO_NOTHING = new ClassAccess(0, 0);

    public static final ClassAccess ACC_PUBLIC = new ClassAccess(Opcodes.ACC_PUBLIC, Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED);
    public static final ClassAccess ACC_PUBLIC_AF = new ClassAccess(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED);
    public static final ClassAccess ACC_PUBLIC_MF = new ClassAccess(Opcodes.ACC_PUBLIC, Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED, Opcodes.ACC_FINAL);

    public static final ClassAccess ACC_PROTECTED = new ClassAccess(Opcodes.ACC_PROTECTED, Opcodes.ACC_PRIVATE);
    public static final ClassAccess ACC_PROTECTED_AF = new ClassAccess(Opcodes.ACC_PROTECTED | Opcodes.ACC_FINAL, Opcodes.ACC_PRIVATE);
    public static final ClassAccess ACC_PROTECTED_MF = new ClassAccess(Opcodes.ACC_PROTECTED, Opcodes.ACC_PRIVATE, Opcodes.ACC_FINAL);

    public static final ClassAccess ACC_PRIVATE = new ClassAccess(Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED, Opcodes.ACC_PUBLIC);
    public static final ClassAccess ACC_PRIVATE_AF = new ClassAccess(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL, Opcodes.ACC_PROTECTED, Opcodes.ACC_PUBLIC);
    public static final ClassAccess ACC_PRIVATE_MF = new ClassAccess(Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED, Opcodes.ACC_PUBLIC, Opcodes.ACC_FINAL);
    private final int access;
    private final int[] incompatible;

    public ClassAccess(
            int access,
            int... incompatible
    ) {
        this.access = access;
        this.incompatible = incompatible;
    }

    public static ClassAccess get(String modifier) {
        switch (modifier) {
            case "public":
                return ACC_PUBLIC;
            case "public-f":
                return ACC_PUBLIC_MF;
            case "public+f":
                return ACC_PUBLIC_AF;
            case "private":
                return ACC_PRIVATE;
            case "private-f":
                return ACC_PRIVATE_MF;
            case "private+f":
                return ACC_PRIVATE_AF;
            case "protected":
                return ACC_PROTECTED;
            case "protected-f":
                return ACC_PROTECTED_MF;
            case "protected+f":
                return ACC_PROTECTED_AF;
            default:
                return ACC_DO_NOTHING;
        }
    }

    public int apply(final int i) {
        int acc = i;
        for (int n : incompatible) acc &= (~n);
        return acc | access;
    }

    public Set<AccessFlag> apply(Set<AccessFlag> flags, AccessFlag.Location location) {
        int incompatMask = 1;
        for (int n : incompatible) incompatMask |= n;
        flags.removeAll(AccessFlag.maskToAccessFlags(incompatMask, location));
        return flags;
    }

    public int access() {
        return access;
    }

    public int[] incompatible() {
        return incompatible;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ClassAccess that = (ClassAccess) obj;
        return this.access == that.access &&
                Objects.equals(this.incompatible, that.incompatible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, incompatible);
    }

    @Override
    public String toString() {
        return "ClassAccess[" +
                "access=" + access + ", " +
                "incompatible=" + incompatible + ']';
    }

}
