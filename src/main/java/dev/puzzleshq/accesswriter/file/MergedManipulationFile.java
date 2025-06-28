package dev.puzzleshq.accesswriter.file;

import dev.puzzleshq.annotation.documentation.Documented;
import dev.puzzleshq.annotation.stability.Stable;

/**
 * Combines all the ManipulationFile.
 *
 * @since 1.0.0
 * @author Mr-Zombii
 */
@Stable
@Documented
public class MergedManipulationFile extends ManipulationFile {

    public MergedManipulationFile() {
        super(null);
    }

    /**
     * Adds the {@link ManipulationFile} to this.
     * @param file the {@link ManipulationFile} to be added.
     */
    public void add(ManipulationFile file) {
        this.classModificationMap.putAll(file.classModificationMap);
        this.fieldModificationMap.putAll(file.fieldModificationMap);
        this.methodModificationMap.putAll(file.methodModificationMap);
    }

}
