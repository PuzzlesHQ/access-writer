package dev.puzzleshq.accesswriter.file;

import dev.puzzleshq.accesswriter.AccessWriters;
import dev.puzzleshq.accesswriter.api.IWriterFormat;
import dev.puzzleshq.accesswriter.util.ClassAccess;

import java.util.HashMap;
import java.util.Map;

// TODO: Document Class

/**
 * @since 1.0.0
 * @author Mr-Zombii
 */
public class ManipulationFile {

    IWriterFormat format;

    public ManipulationFile(IWriterFormat format) {
        this.format = format;
    }

    public IWriterFormat getFormat() {
        return format;
    }

    /* class, modifier */
    Map<String, ClassAccess> classModificationMap = new HashMap<>();
    /* class~field, modifier> */
    Map<String, ClassAccess> fieldModificationMap = new HashMap<>();
    /* class~descriptor~method, modifier> */
    Map<String, ClassAccess> methodModificationMap = new HashMap<>();

    public void add(String modifier, String clazz) {
        classModificationMap.put(AccessWriters.getRemapper().remap(clazz), ClassAccess.get(modifier));
    }

    public void add(String modifier, String clazz, String name) {
        fieldModificationMap.put(AccessWriters.getRemapper().remap(clazz, name), ClassAccess.get(modifier));
    }

    public void add(String modifier, String clazz, String name, String descriptor) {
        methodModificationMap.put(AccessWriters.getRemapper().remap(clazz, name, descriptor), ClassAccess.get(modifier));
    }

    public boolean has(String name) {
        name = AccessWriters.getRemapper().remap(name);
        return classModificationMap.containsKey(name) | methodModificationMap.containsKey(name) | fieldModificationMap.containsKey(name);
    }

    public ClassAccess get(String clazz) {
        clazz = AccessWriters.getRemapper().remap(clazz);
        ClassAccess access = classModificationMap.get(clazz);
        if (access == null) return ClassAccess.ACC_DO_NOTHING;
        return access;
    }

    public ClassAccess get(String clazz, String field) {
        ClassAccess access = fieldModificationMap.get(AccessWriters.getRemapper().remap(clazz, field));
        if (access == null) return ClassAccess.ACC_DO_NOTHING;
        return access;
    }

    public ClassAccess get(String clazz, String method, String descriptor) {
        ClassAccess access = methodModificationMap.get(AccessWriters.getRemapper().remap(clazz, method, descriptor));
        if (access == null) return ClassAccess.ACC_DO_NOTHING;
        return access;
    }

    public void clear() {
        classModificationMap.clear();
        fieldModificationMap.clear();
        methodModificationMap.clear();
    }
}
