package dev.puzzleshq.accesswriter;

import dev.puzzlehq.annotation.Internal;
import dev.puzzlehq.annotation.documentation.Documented;
import dev.puzzlehq.annotation.stability.Stable;
import dev.puzzleshq.accesswriter.api.IWriterFormat;
import dev.puzzleshq.accesswriter.api.IRemapper;
import dev.puzzleshq.accesswriter.api.impl.format.AccessManipulatorFormat;
import dev.puzzleshq.accesswriter.api.impl.format.FabricAccessWidenerFormat;
import dev.puzzleshq.accesswriter.api.impl.format.ForgeAccessTransformerFormat;
import dev.puzzleshq.accesswriter.api.impl.remapping.NullRemapper;
import dev.puzzleshq.accesswriter.file.MergedManipulationFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0.0
 * @author Mr-Zombii
 */
@Stable
@Documented
public class AccessWriters {

    public static final MergedManipulationFile MERGED = new MergedManipulationFile();

    @Internal
    private static IRemapper remapper = new NullRemapper();

    @Internal
    private static final Map<String, IWriterFormat> formatMap = new HashMap<>();

    /**
     * Initialises accessWriter.
     * @param loader the {@link ClassLoader} you are using.
     */
    public static void init(ClassLoader loader) {
        MERGED.clear();

        initDefaultFormats();

        if (loader == null) return;
        Class<?> loaderClass = loader.getClass();
        if (loader.getClass().getName().equals("dev.puzzleshq.loader.launch.PieceClassLoader")) {
            try {
                Method addExclusion = loaderClass.getMethod("addClassLoaderExclusion", String.class);
                addExclusion.invoke(loader, "dev.puzzleshq.manipulators.transformers");
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
            }
        }
    }

    /**
     * Registers the Remapper to be used.
     * @param remapper the fileReader to register.
     * @see IRemapper
     */
    public static void register(IRemapper remapper) {
        AccessWriters.remapper = remapper;
    }

    /**
     * Registers format to be used for the file extension.
     * @param extension The extension of the file that is format applies to .cfg, .accesswidener, .manipulator.
     * @see IWriterFormat
     */
    public static void register(String extension, IWriterFormat format) {
        formatMap.put(extension, format);
    }

    /**
     * Gets the format of the file.
     * @param fileName the full name of the file.
     *
     * @return {@link IWriterFormat}
     */
    public static IWriterFormat getFormat(String fileName) {
        String[] parts = fileName.split("\\.");
        return formatMap.get(".".concat(parts[parts.length - 1]));
    }

    /**
     * Gets the registered remapper.
     * @see AccessWriters#register(IRemapper)
     *
     * @return {@link IRemapper}
     */
    public static IRemapper getRemapper() {
        return remapper;
    }

    /**
     * Initialises the default formats.
     */
    public static void initDefaultFormats() {
        register(".manipulator", new AccessManipulatorFormat());
        register(".cfg", new ForgeAccessTransformerFormat());
        register(".accesswidener", new FabricAccessWidenerFormat());
    }

}
