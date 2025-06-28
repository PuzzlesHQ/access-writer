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
