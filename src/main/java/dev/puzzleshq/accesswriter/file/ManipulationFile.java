package dev.puzzleshq.accesswriter.file;

import dev.puzzleshq.accesswriter.AccessWriters;
import dev.puzzleshq.accesswriter.api.IWriterFormat;
import dev.puzzleshq.accesswriter.util.ClassAccess;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that contains the class, field and method modification.
 *
 * @since 1.0.0
 * @author Mr-Zombii
 */
public class ManipulationFile {

    IWriterFormat format;

    public ManipulationFile(IWriterFormat format) {
        this.format = format;
    }

    /**
     * Gets the format of the ManipulationFile.
     */
    public IWriterFormat getFormat() {
        return format;
    }

    /* class, modifier */
    Map<String, ClassAccess> classModificationMap = new HashMap<>();
    /* class~field, modifier> */
    Map<String, ClassAccess> fieldModificationMap = new HashMap<>();
    /* class~descriptor~method, modifier> */
    Map<String, ClassAccess> methodModificationMap = new HashMap<>();

    /**
     * Adds the class modifications.
     * @param modifier the modifier to use.
     * @param clazz the class to modify.
     * @see ClassAccess
     */
    public void add(String modifier, String clazz) {
        classModificationMap.put(AccessWriters.getRemapper().remap(clazz), ClassAccess.get(modifier));
    }

    /**
     * Adds the field modifications.
     * @param modifier the modifier to use.
     * @param clazz the class the field is in.
     * @param name the fields name.
     * @see ClassAccess
     */
    public void add(String modifier, String clazz, String name) {
        fieldModificationMap.put(AccessWriters.getRemapper().remap(clazz, name), ClassAccess.get(modifier));
    }

    /**
     * Adds the method modifications.
     * @param modifier the modifier to use.
     * @param clazz the class the method is in.
     * @param name the methods name.
     * @param descriptor the methods descriptor.
     * @see ClassAccess
     */
    public void add(String modifier, String clazz, String name, String descriptor) {
        methodModificationMap.put(AccessWriters.getRemapper().remap(clazz, name, descriptor), ClassAccess.get(modifier));
    }

    /**
     * Checks if the ManipulationFile has the class/method/field.
     * @param name the full path.
     */
    public boolean has(String name) {
        name = AccessWriters.getRemapper().remap(name);
        return classModificationMap.containsKey(name) | methodModificationMap.containsKey(name) | fieldModificationMap.containsKey(name);
    }

    /**
     * Gets the new access for the class.
     * @param clazz the class to get the new access of.
     * @return {@link  ClassAccess#ACC_DO_NOTHING} if no modification is done.
     */
    public ClassAccess get(String clazz) {
        clazz = AccessWriters.getRemapper().remap(clazz);
        ClassAccess access = classModificationMap.get(clazz);
        if (access == null) return ClassAccess.ACC_DO_NOTHING;
        return access;
    }

    /**
     * Gets the new access for the field.
     * @param clazz The class that contains the field.
     * @param field the field to get the new access of.
     * @return {@link  ClassAccess#ACC_DO_NOTHING} if no modification is done.
     */
    public ClassAccess get(String clazz, String field) {
        ClassAccess access = fieldModificationMap.get(AccessWriters.getRemapper().remap(clazz, field));
        if (access == null) return ClassAccess.ACC_DO_NOTHING;
        return access;
    }

    /**
     * Gets the new access for the method.
     * @param clazz The class that contains the method.
     * @param method the method to get the new access of.
     * @param descriptor the methods descriptor.
     * @return {@link  ClassAccess#ACC_DO_NOTHING} if no modification is done.
     */
    public ClassAccess get(String clazz, String method, String descriptor) {
        ClassAccess access = methodModificationMap.get(AccessWriters.getRemapper().remap(clazz, method, descriptor));
        if (access == null) return ClassAccess.ACC_DO_NOTHING;
        return access;
    }

    /**
     * Clears the manipulation file.
     */
    public void clear() {
        classModificationMap.clear();
        fieldModificationMap.clear();
        methodModificationMap.clear();
    }
}
