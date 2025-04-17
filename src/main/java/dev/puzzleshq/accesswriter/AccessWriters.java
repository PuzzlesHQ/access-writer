package dev.puzzleshq.accesswriter;

import dev.puzzleshq.accesswriter.api.IWriterFormat;
import dev.puzzleshq.accesswriter.api.IFileReader;
import dev.puzzleshq.accesswriter.api.IRemapper;
import dev.puzzleshq.accesswriter.api.impl.format.AccessManipulatorFormat;
import dev.puzzleshq.accesswriter.api.impl.format.FabricAccessWidenerFormat;
import dev.puzzleshq.accesswriter.api.impl.format.ForgeAccessTransformerFormat;
import dev.puzzleshq.accesswriter.api.impl.remapping.NullRemapper;
import dev.puzzleshq.accesswriter.file.ManipulationFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

// TODO: Document Class

/**
 * @since 1.0.0
 * @author Mr-Zombii
 */
public class AccessWriters {

    public static final ManipulationFile MERGED = new ManipulationFile(null);

    private static IFileReader reader;
    private static IRemapper remapper = new NullRemapper();

    private static final Map<String, IWriterFormat> formatMap = new HashMap<>();

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

    public static void register(IFileReader reader) {
        AccessWriters.reader = reader;
    }

    public static void register(IRemapper remapper) {
        AccessWriters.remapper = remapper;
    }

    /**
     * @param extension The extension of the file that is format applies to .cfg, .accesswidener, .manipulator
     */
    public static void register(String extension, IWriterFormat format) {
        formatMap.put(extension, format);
    }

    public IWriterFormat getFormat(String fileName) {
        String[] parts = fileName.split("\\.");
        return formatMap.get(".".concat(parts[parts.length - 1]));
    }

    public static IRemapper getRemapper() {
        return remapper;
    }

    public static IFileReader getReader() {
        return reader;
    }

    public static void initDefaultFormats() {
        register(".manipulator", new AccessManipulatorFormat());
        register(".cfg", new ForgeAccessTransformerFormat());
        register(".accesswidener", new FabricAccessWidenerFormat());
    }

}
