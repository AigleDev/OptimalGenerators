package me.aiglez.optimalgenerators.backend;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.aiglez.optimalgenerators.OptimalGenerators;
import me.aiglez.optimalgenerators.utils.UL;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Updater {

    private static Updater instance;
    private String latestVersion;
    private String latestTag;
    private List<String> latestChanges = new ArrayList<>();

    private String currentVersion;
    private String currentTag;


    public Updater() {
        instance = this;
        String[] versionSplit = OptimalGenerators.instance().getVersion().split("-");
        currentVersion = versionSplit[0];
        currentTag = versionSplit[1];
    }


    public void checkUpdates() {
        UL.log("------------------------------------");
        UL.log("Checking for updates...");
        Thread updater = new Thread(() -> {
            try {
                String content = IOUtils.toString(new URL("https://gist.githubusercontent.com/AigleDev/e5108557a55eeef8e109e4a5759cb4e5/raw/447a8aca442fc41c8b84edb8e7b672bc7764b8df/optimalgenerators.json"), Charset.defaultCharset());

                JsonParser parser = new JsonParser();
                JsonElement json = parser.parse(content);

                this.latestVersion = json.getAsJsonObject().get("version").getAsString();
                this.latestTag = json.getAsJsonObject().get("tag").getAsString();
                boolean hotfix = json.getAsJsonObject().get("hotfix").getAsBoolean();

                latestChanges.clear();
                for (JsonElement element : json.getAsJsonObject().get("changes").getAsJsonArray()) {
                    latestChanges.add(element.getAsString());
                }


                if(shouldUpdate()) {
                    UL.log("New update was found, latest version: " + latestVersion + " (" + latestTag + "), current version: " + currentVersion + " (" + currentTag + ").");
                    if(hotfix) UL.log("You need to update ASAP this is a HOTFIX !");
                    UL.log("Latest version changes: ");
                    latestChanges.forEach(change -> UL.log("- " + change));
                }


            } catch (IOException e) {
                UL.error("An error occurred while checking for updates..");
                e.printStackTrace();
            }
        }, "OptimalGenerators-Updater");

        updater.start();
    }

    public List<String> getLatestChanges() { return Collections.unmodifiableList(latestChanges); }

    public String getLatestTag() { return latestTag; }

    public String getLatestVersion() { return latestVersion; }

    public String getCurrentTag() { return currentTag; }

    public String getCurrentVersion() { return currentVersion; }

    // RELEASE
    // BUILD


    private boolean shouldUpdate() {
        if(Double.parseDouble(latestVersion) > Double.parseDouble(currentVersion)) {
            return true;
        }

        if(latestTag.equalsIgnoreCase("release")) return true;
        if(latestTag.equalsIgnoreCase("build") && currentTag.equalsIgnoreCase("release")) return false;

        return false;
    }

    public static Updater getInstance() {
        return instance;
    }
}
