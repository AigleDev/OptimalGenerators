package me.aiglez.optimalgenerators.api.enums;

import org.jetbrains.annotations.Nullable;

public enum CommandExecutor {

    CONSOLE("console"),
    PLAYER("player");

    private String name;

    CommandExecutor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static @Nullable CommandExecutor getByName(String name) {
        for(CommandExecutor value : values()) {
            if(value.getName().equalsIgnoreCase(name)) return value;
        }
        return null;
    }


    @Override
    public String toString() {
        return getName();
    }
}
