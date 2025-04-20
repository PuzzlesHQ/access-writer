package dev.puzzleshq.accesswriter.util;

import dev.puzzlehq.annotation.stability.Stable;
import org.objectweb.asm.Opcodes;

@Stable
public record ClassAccess(
        int access,
        int... incompatible
) {

    public static final ClassAccess ACC_DO_NOTHING = new ClassAccess(0, 0);

    public static final ClassAccess ACC_PUBLIC = new ClassAccess(Opcodes.ACC_PUBLIC, Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED);
    public static final ClassAccess ACC_PUBLIC_AF = new ClassAccess(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED);
    public static final ClassAccess ACC_PUBLIC_MF = new ClassAccess(Opcodes.ACC_PUBLIC, Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED, Opcodes.ACC_FINAL);

    public static final ClassAccess ACC_PROTECTED = new ClassAccess(Opcodes.ACC_PROTECTED, Opcodes.ACC_PRIVATE);
    public static final ClassAccess ACC_PROTECTED_AF = new ClassAccess(Opcodes.ACC_PROTECTED | Opcodes.ACC_FINAL,Opcodes.ACC_PRIVATE);
    public static final ClassAccess ACC_PROTECTED_MF = new ClassAccess(Opcodes.ACC_PROTECTED, Opcodes.ACC_PRIVATE, Opcodes.ACC_FINAL);

    public static final ClassAccess ACC_PRIVATE = new ClassAccess(Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED, Opcodes.ACC_PUBLIC);
    public static final ClassAccess ACC_PRIVATE_AF = new ClassAccess(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL, Opcodes.ACC_PROTECTED, Opcodes.ACC_PUBLIC);
    public static final ClassAccess ACC_PRIVATE_MF = new ClassAccess(Opcodes.ACC_PRIVATE, Opcodes.ACC_PROTECTED, Opcodes.ACC_PUBLIC, Opcodes.ACC_FINAL);

    public static ClassAccess get(String modifier) {
        return switch (modifier) {
            case "public" -> ACC_PUBLIC;
            case "public-f" -> ACC_PUBLIC_MF;
            case "public+f" -> ACC_PUBLIC_AF;
            case "private" -> ACC_PRIVATE;
            case "private-f" -> ACC_PRIVATE_MF;
            case "private+f" -> ACC_PRIVATE_AF;
            case "protected" -> ACC_PROTECTED;
            case "protected-f" -> ACC_PROTECTED_MF;
            case "protected+f" -> ACC_PROTECTED_AF;
            default -> ACC_DO_NOTHING;
        };
    }

    public int apply(final int i) {
        int acc = i;
        for (int n : incompatible) acc &= (~n);
        return acc | access;
    }
}
