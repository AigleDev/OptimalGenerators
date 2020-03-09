package me.aiglez.optimalgenerators.utils.chat;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ML {

    public static String tl(String message) {
        Validate.notNull(message);

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> tl(List<String> messages) {
        Validate.noNullElements(messages);

        return messages.stream().map(ML::tl).collect(Collectors.toList());
    }

    public static String tl(String message, Placeholder... placeholders) {
        Validate.notNull(message);
        Validate.noNullElements(placeholders);

        for (Placeholder placeholder : placeholders) {
            placeholder.replace(message);
        }

        return tl(message);
    }

    public static List<String> tl(List<String> messages, Placeholder... placeholders) {
        Validate.noNullElements(messages);
        Validate.noNullElements(placeholders);

        List<String> translated = new ArrayList<>();
        messages.forEach(message -> {
            for (Placeholder placeholder : placeholders) {
                placeholder.replace(message);
            }

            translated.add(tl(message));
        });

        return translated;
    }
}
