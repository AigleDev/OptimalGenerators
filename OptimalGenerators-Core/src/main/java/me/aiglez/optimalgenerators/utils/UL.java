package me.aiglez.optimalgenerators.utils;

import me.aiglez.optimalgenerators.OptimalGenerators;
import me.aiglez.optimalgenerators.utils.chat.ML;
import org.apache.commons.lang.Validate;


public class UL {

    private static OptimalGenerators instance = OptimalGenerators.instance();

    // -------------------------------------------- //
    // LOGGERS
    // -------------------------------------------- //
    public static void debug(String message) {
        Validate.notNull(message, "message may not be null");

        instance.getServer().getConsoleSender().sendMessage("§3[OptimalGenerators - DEBUG] §f" + ML.tl(message));
    }

    public static void log(String message) {
        Validate.notNull(message, "message may not be null");

        instance.getServer().getConsoleSender().sendMessage("§b[OptimalGenerators - LOG] §f" + ML.tl(message));
    }

    public static void error(String message) {
        Validate.notNull(message, "message may not be null");

        instance.getServer().getConsoleSender().sendMessage("§c[OptimalGenerators - ERROR] " + ML.tl(message));
    }

    public static void enabling(String message) {
        Validate.notNull(message, "message may not be null");

        instance.getServer().getConsoleSender().sendMessage("§a[OptimalGenerators - ENABLING] §f" + ML.tl(message));
    }

}
