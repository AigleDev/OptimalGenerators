package me.aiglez.optimalgenerators.backend.files;

import me.aiglez.optimalgenerators.utils.chat.ML;
import me.aiglez.optimalgenerators.utils.chat.Placeholder;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public enum Language {

    // -------------------------------------------- //
    // GLOBAL
    // -------------------------------------------- //
    PREFIX("prefix", "&c&l[!] &r"),
    RELOADED("reloaded", "");


    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //
    private static FileConfiguration file;

    private String path;
    private String defaultMessage;
    private List<String> defaultListMessage;

    // -------------------------------------------- //
    // MAIN
    // -------------------------------------------- //
    Language(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    Language(String path, List<String> defaultListMessage) {
        this.path = path;
        this.defaultListMessage = defaultListMessage;
    }

    // -------------------------------------------- //
    // METHODS
    // -------------------------------------------- //

    // return the colored message
    @Override
    public String toString() {
        return ML.tl(get(path, true));
    }

    public String translate(Placeholder... placeholders) {
        return ML.tl(get(path, true), placeholders);
    }

    public String translateNoPrefix(Placeholder... placeholders) {
        return ML.tl(get(path, false), placeholders);
    }

    public List<String> translateList(Placeholder... placeholders) {
        return ML.tl(get(path), placeholders);
    }


    // -------------------------------------------- //
    // UTILS
    // -------------------------------------------- //
    // Fetch message
    private String get(String path, boolean prefix) {
        if(file.contains("messages." + path)) {
            return (prefix ? PREFIX + file.getString("messages." + path) : file.getString("messages." + path));
        } else return (prefix ? PREFIX + defaultMessage : defaultMessage);
    }

    private List<String> get(String path) {
        if(file.contains("messages." + path)) {
            return file.getStringList("messages." + path);
        } else return defaultListMessage;
    }


    public static void setFile(FileConfiguration file) {
        Validate.notNull(file);
        Language.file = file;
    }
}
