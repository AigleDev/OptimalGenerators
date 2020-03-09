package me.aiglez.optimalgenerators.api.enums;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Nullable;

public enum GenerationType {

    ITEM("item"),
    COMMAND("command");

    private String name;
    GenerationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static @Nullable GenerationType getByName(String name) {
        Validate.notNull(name, "name may not be null");
        for(GenerationType value : values()) {
            if(value.getName().equalsIgnoreCase(name)) return value;
        }

        return null;
    }


    @Override
    public String toString() {
        return getName();
    }
}
