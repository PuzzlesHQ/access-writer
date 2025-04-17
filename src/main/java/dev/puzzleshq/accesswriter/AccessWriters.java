package dev.puzzleshq.accesswriter;

import dev.puzzleshq.accesswriter.api.IWriterFormat;
import dev.puzzleshq.accesswriter.api.IFileReader;
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
public class AccessWriters {

    public static final MergedManipulationFile MERGED = new MergedManipulationFile();

    private static IFileReader reader;
    private static IRemapper remapper = new NullRemapper();

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
     * Registers the FileReader to be used.
     * @param reader the fileReader to register.
     * @see IFileReader
     */
    public static void register(IFileReader reader) {
        AccessWriters.reader = reader;
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
     */
    public IWriterFormat getFormat(String fileName) {
        String[] parts = fileName.split("\\.");
        return formatMap.get(".".concat(parts[parts.length - 1]));
    }

    /**
     * Gets the registered remapper.
     * @see AccessWriters#register(IRemapper)
     */
    public static IRemapper getRemapper() {
        return remapper;
    }

    /**
     * Gets the registered reader.
     * @see AccessWriters#register(IFileReader)
     */
    public static IFileReader getReader() {
        return reader;
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
