package dev.puzzleshq.accesswriter.api.impl.format;

import dev.puzzleshq.annotation.Internal;
import dev.puzzleshq.annotation.stability.Stable;
import dev.puzzleshq.accesswriter.file.ManipulationFile;
import dev.puzzleshq.accesswriter.api.IWriterFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An internal class that parses the AccessTransformer format that was created by MinecraftForge
 * <br>
 * <br>
 * <strong>Resources</strong>
 * <br>
 * <ul>
 *     <a href="https://docs.minecraftforge.net/en/latest/advanced/accesstransformers/">Format documentation</a>
 * </ul>
 *
 * @since 1.0.0
 * @author Mr-Zombii
 */
@Stable
@Internal
public class ForgeAccessTransformerFormat implements IWriterFormat {

    ManipulationFile file;

    @Override
    public ManipulationFile parse(String contents) {
        file = new ManipulationFile(this);

        Scanner scanner = new Scanner(contents);

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) lines.add(scanner.nextLine());

        for (String line : lines) {
            if (line.replaceAll("[ \r\n]", "").isEmpty() || line.startsWith("#")) continue;

            String[] parts = line.split(" ");

            verify(parts[0], parts[1], parts);
        }
        return file;
    }

    private void verify(String modifier, String type, String[] parts) {
        if (
                !modifier.equals("public") && !modifier.equals("private") && !modifier.equals("protected") &&
                        !modifier.equals("public-f") && !modifier.equals("private-f") && !modifier.equals("protected-f") &&
                        !modifier.equals("public+f") && !modifier.equals("private+f") && !modifier.equals("protected+f")
        )
            throw new IllegalArgumentException("Modifier must be either public, private, or protected with the optional +f or -f.");

        switch (type) {
            case "class":
                if (parts.length != 2) throw new IllegalArgumentException("Wrong argument for a class modifier, it must be in similar to \"<modifier> <full-class-name>\"");
                file.add(modifier, parts[1].replaceAll("\\.", "/"));
                break;
            case "field":
                if (parts.length != 3) throw new IllegalArgumentException("Wrong argument for a field modifier, it must be in similar to \"<modifier> <full-class-name> <field-name>\"");
                file.add(modifier, parts[1].replaceAll("\\.", "/"), parts[2]);
                break;
            case "method":
                if (parts.length != 4) throw new IllegalArgumentException("Wrong argument for a method modifier, it must be in similar to \"<modifier> <full-class-name> <method-name> <method-descriptor>\"");
                file.add(modifier, parts[1].replaceAll("\\.", "/"), parts[2], parts[3]);
                break;
            default:
                throw new RuntimeException("Unknown Token \"" + type + "\".");
        }
    }

    @Override
    public String name() {
        return "Forge Access Transformer";
    }
}
