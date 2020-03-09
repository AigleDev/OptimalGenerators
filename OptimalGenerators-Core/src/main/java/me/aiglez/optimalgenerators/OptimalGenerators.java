package me.aiglez.optimalgenerators;

import me.aiglez.optimalgenerators.api.OptimalNBT;
import me.aiglez.optimalgenerators.backend.Updater;
import me.aiglez.optimalgenerators.backend.files.Configuration;
import me.aiglez.optimalgenerators.libby.DependencyManager;
import me.aiglez.optimalgenerators.utils.UL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class OptimalGenerators extends JavaPlugin implements me.aiglez.optimalgenerators.api.OptimalGenerators {

    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //
    private static OptimalGenerators instance; // our instance ;)
    private static OptimalNBT optimalNBT;
    private boolean loaded;

    // -------------------------------------------- //
    // MAIN
    // -------------------------------------------- //

    @Override
    public void onLoad() {
        DependencyManager dependencyManager = new DependencyManager(this, getDataFolder().toPath());

        dependencyManager.addMavenCentral();
        try {
            Class.forName("com.google.gson.Gson");
        } catch (ClassNotFoundException e) {
            System.out.println("[OptimalGenerators] [Dependency] GSON not found downloading it...");
            try {
                dependencyManager.loadGSON();
            } catch (RuntimeException ex) {
                System.out.println("[OptimalGenerators] [Dependency] Couldn't download GSON...");
                ex.printStackTrace();
                loaded = false;

                return;
            }
        }


        try {
            Class.forName("org.apache.commons.io.IOUtils");
        } catch (ClassNotFoundException e) {
            System.out.println("[OptimalGenerators] [Dependency] IOUtils (commons) not found downloading it...");
            try {
                dependencyManager.loadGSON();
            } catch (RuntimeException ex) {
                System.out.println("[OptimalGenerators] [Dependency] Couldn't download IO Utils...");
                ex.printStackTrace();
                loaded = false;

                return;
            }
        }
        loaded = true;
    }

    @Override
    public void onEnable() {
        long startMillis = System.currentTimeMillis();
        instance = this;

        UL.enabling(String.format("Starting OptimalGenerators by AigleZ (v.%s) ...", getVersion()));
        if(!loaded) {
            UL.error("An error occurred while loading the plugin. Disabling !");
            disable();
            return;
        }

        if(!isSupported()) {
            UL.error("----------------------------------------------");
            UL.error("Seems like your spigot version isn't supported");
            UL.error("Requirements:");
            UL.error("- Java 8 or above");
            UL.error("- CraftBukkit / Spigot (and other forks*)");
            System.out.println(" ");
            UL.error("- (*) Only if they don't beak Bukkit API.");
            UL.error("Suggestions:");
            UL.error("- We highly recommend you to switch to Paper or OptimalSpigot \n(OptimalSpigot is made by our team)");
            UL.error("----------------------------------------------");
            disable();

            return;
        }

        new Updater();

        UL.enabling("Creating configurations files...");
        new Configuration(this);
        loadNBTProvider();

        UL.enabling("§e[!] Keep in mind this a dev build, do not use on production environments !");
        UL.enabling(String.format("&aOptimalGenerators fully loaded (took: %s) ...", (System.currentTimeMillis() - startMillis)));

        Updater.getInstance().checkUpdates();

    }

    @Override
    public void onDisable() {

    }

    // -------------------------------------------- //
    // METHODS
    // -------------------------------------------- //
    public static OptimalGenerators instance() { return instance; }

    public void disable() {
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private void loadNBTProvider() {
        UL.enabling("[OptimalNBT] Loading OptimalNBT...");

        String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        UL.enabling("[OptimalNBT] Found internals: &a" + internalsName);

        try {
            optimalNBT = (OptimalNBT) Class.forName("me.aiglez.optimalgenerators.nms." + internalsName + ".OptimalNBT").newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            UL.error("[OptimalNBT] Could not find a valid implementation for this server version. Disabling.");
            //disable();
        }
    }

    public static OptimalNBT optimalNBT() {
        return optimalNBT;
    }

    // -------------------------------------------- //
    // API
    // -------------------------------------------- //
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public String getAPIVersion() {
        return "1.0";
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }


}
