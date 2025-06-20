package dev.puzzleshq.accesswriter.transformers;

import dev.puzzleshq.accesswriter.AccessWriters;

import java.lang.classfile.*;
import java.util.List;

public class AccessTransformer24 {

    public static byte[] transform(byte[] bytes) {
        ClassFile classFile = ClassFile.of();
        ClassModel model = classFile.parse(bytes);

        String internalName = model.thisClass().asInternalName();

        return classFile.transformClass(model, (builder, ce) -> {
            int access = AccessWriters.MERGED.get(internalName).apply(model.flags().flagsMask());
            builder.withFlags(access);
            List<MethodModel> methodModels = model.methods();
            for (MethodModel method : methodModels) {
                int methodAccess = AccessWriters.MERGED.get(internalName, method.methodName().stringValue(), method.methodType().stringValue()).apply(method.flags().flagsMask());
                builder.transformMethod(method, (b, e) -> {
                    b.withFlags(methodAccess);
                });
            }

            List<FieldModel> fieldModels = model.fields();
            for (FieldModel field : fieldModels) {
                int methodAccess = AccessWriters.MERGED.get(internalName, field.fieldName().stringValue()).apply(field.flags().flagsMask());
                builder.transformField(field, (b, e) -> {
                    b.withFlags(methodAccess);
                });
            }
        });
    }

}
