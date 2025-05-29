package dev.puzzleshq.accesswriter.transformers;

import dev.puzzleshq.accesswriter.AccessWriters;

import java.lang.classfile.*;
import java.util.List;

public class AccessTransformer24 {

    public static byte[] transform(String internalName, byte[] bytes) {
        ClassFile classFile = ClassFile.of();
        ClassModel model = classFile.parse(bytes);

        return classFile.transformClass(model, (builder, ce) -> {
            int access = AccessWriters.MERGED.get(internalName).apply(model.flags().flagsMask());
            builder.withFlags(access);
            List<MethodModel> methodModels = model.methods();
            for (MethodModel method : methodModels) {
                int methodAccess = AccessWriters.MERGED.get(internalName, method.methodName().stringValue(), method.methodType().stringValue()).apply(access);
                builder.transformMethod(method, (b, e) -> {
                    b.withFlags(methodAccess);
                });
            }

            List<FieldModel> fieldModels = model.fields();
            for (FieldModel field : fieldModels) {
                int methodAccess = AccessWriters.MERGED.get(internalName, field.fieldName().stringValue()).apply(access);
                builder.transformField(field, (b, e) -> {
                    b.withFlags(methodAccess);
                });
            }
        });
    }

}
