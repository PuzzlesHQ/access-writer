package dev.puzzleshq.accesswriter.api;

import dev.puzzleshq.annotation.documentation.Documented;
import dev.puzzleshq.annotation.stability.Stable;
import dev.puzzleshq.accesswriter.file.ManipulationFile;

/**
 * A format outline that parses and gives the name of the format implemented.
 *
 * @since 1.0.0
 * @author Mr-Zombii
 */
@Stable
@Documented
public interface IWriterFormat {

    /**
     * Parses contents into a manipulation file.
     *
     * @param contents The contents of a manipulator file that are to be parsed.
     * @return {@link ManipulationFile}
     */
    ManipulationFile parse(String contents);

    /**
     * Gets and returns the name of whatever custom format implemented this interface.
     *
     * @return the name of format.
     */
    String name();

}
