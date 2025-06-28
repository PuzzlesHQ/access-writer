package dev.puzzleshq.accesswriter.api;

import dev.puzzleshq.annotation.documentation.Documented;
import dev.puzzleshq.annotation.stability.Stable;

/**
 * An interface service to remap classes from deobfuscated to obfuscated namings.
 *
 * @since 1.0.0
 * @author Mr-Zombii
 */
@Stable
@Documented
public interface IRemapper {

    /**
     * @param clazz Unobfuscated class name.
     * @return obfuscated class string for storage in the manipulation file.
     */
    String remap(String clazz);

    /**
     * @param clazz Unobfuscated class name.
     * @param field Unobfuscated field name.
     * @return obfuscated class~field string for storage in the manipulation file.
     */
    String remap(String clazz, String field);

    /**
     * @param clazz Unobfuscated class name.
     * @param method Unobfuscated method name.
     * @param descriptor Unobfuscated method descriptor.
     * @return obfuscated class~descriptor~method string for storage in the manipulation file.
     */
    String remap(String clazz, String method, String descriptor);

}
