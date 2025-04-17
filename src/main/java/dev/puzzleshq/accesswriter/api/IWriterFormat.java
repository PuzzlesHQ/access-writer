package dev.puzzleshq.accesswriter.api;

import dev.puzzleshq.accesswriter.file.ManipulationFile;

/**
 * A format outline that parses and gives the name of the format implemented.
 *
 * @since 1.0.0
 * @author Mr-Zombii
 */
public interface IWriterFormat {

    /**
     * @param contents The contents of a manipulator file that are to be parsed.
     *
     * @return {@link ManipulationFile}
     */
    ManipulationFile parse(String contents);

    /**
     * @return Name of implemented format.
     */
    String name();

}
