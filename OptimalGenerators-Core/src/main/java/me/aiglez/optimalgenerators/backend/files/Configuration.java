package me.aiglez.optimalgenerators.backend.files;

import com.google.common.base.Charsets;
import me.aiglez.optimalgenerators.OptimalGenerators;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Configuration {

    private static OptimalGenerators instance;
    private static FileConfiguration config;
    private static File file;

    public Configuration(OptimalGenerators instance) {
        Validate.notNull(instance);

        Configuration.instance = instance; // set instance

        if(!instance.getDataFolder().exists()) instance.getDataFolder().mkdirs(); // create dir if not created (usually not)
        file = new File(instance.getDataFolder(), "config.yml");

        if (!file.exists()) instance.saveResource("config.yml", false);

        reloadConfig();
    }

    public static void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        InputStream defConfigStream = instance.getResource("config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        if (defConfigStream != null) {
            config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        }
    }

    public static FileConfiguration instance() {
        if (config == null) {
            reloadConfig();
        }

        return config;
    }
}
