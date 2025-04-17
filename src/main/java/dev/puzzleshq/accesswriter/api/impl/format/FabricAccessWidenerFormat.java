package dev.puzzleshq.accesswriter.api.impl.format;

import dev.puzzleshq.accesswriter.file.ManipulationFile;
import dev.puzzleshq.accesswriter.api.IFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FabricAccessWidenerFormat implements IFormat {

    ManipulationFile file;

    @Override
    public ManipulationFile parse(String contents) {
        file = new ManipulationFile(this);

        Scanner scanner = new Scanner(contents);

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) lines.add(scanner.nextLine());

        for (String line : lines) {
            if (line.isBlank() || line.startsWith("#") || line.startsWith("accessWidener")) continue;

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
                if (parts.length != 3) throw new IllegalArgumentException("Wrong argument for a class modifier, it must be in similar to \"<modifier> class <full-class-name>\"");
                file.add(toGenericModifier(modifier), parts[2].replaceAll("\\.", "/"));
            case "field":
                if (parts.length != 5) throw new IllegalArgumentException("Wrong argument for a field modifier, it must be in similar to \"<modifier> field <full-class-name> <field-name>\"");
                file.add(toGenericModifier(modifier), parts[2].replaceAll("\\.", "/"), parts[3]);
            case "method":
                if (parts.length != 5) throw new IllegalArgumentException("Wrong argument for a method modifier, it must be in similar to \"<modifier> field <full-class-name> <method-name> <method-descriptor>\"");
                file.add(toGenericModifier(modifier), parts[2].replaceAll("\\.", "/"), parts[3], parts[4]);
            default:
                throw new RuntimeException("Unknown Token \"" + type + "\".");
        }
    }

    private String toGenericModifier(String modifier) {
        return switch (modifier) {
            case "accessible" -> "public";
            case "extendable" -> "protected-f";
            case "mutable" -> "public-f";
            default -> throw new RuntimeException("Unsupported access: '" + modifier + "'");
        };
    }

    @Override
    public String name() {
        return "Fabric Access Widener";
    }
}
