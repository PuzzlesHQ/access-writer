package dev.puzzleshq.accesswriter.api.impl.remapping;

import dev.puzzleshq.annotation.Internal;
import dev.puzzleshq.annotation.stability.Stable;
import dev.puzzleshq.accesswriter.api.IRemapper;

@Stable
@Internal
public class NullRemapper implements IRemapper {

    @Override
    public String remap(String clazz) {
        return clazz;
    }

    @Override
    public String remap(String clazz, String field) {
        return clazz.concat("~").concat(field);
    }

    @Override
    public String remap(String clazz, String method, String descriptor) {
        return clazz.concat("~").concat(descriptor).concat("~").concat(method);
    }
}
