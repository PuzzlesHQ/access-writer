package dev.puzzleshq.accesswriter.file;

// TODO: Document Class

/**
 * @since 1.0.0
 * @author Mr-Zombii
 */
public class MergedManipulationFile extends ManipulationFile {

    public MergedManipulationFile() {
        super(null);
    }

    public void add(ManipulationFile file) {
        this.classModificationMap.putAll(file.classModificationMap);
        this.fieldModificationMap.putAll(file.fieldModificationMap);
        this.methodModificationMap.putAll(file.methodModificationMap);
    }

}
