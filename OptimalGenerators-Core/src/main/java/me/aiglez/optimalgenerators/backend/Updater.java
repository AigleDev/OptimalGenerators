package me.aiglez.optimalgenerators.backend;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.aiglez.optimalgenerators.OptimalGenerators;
import me.aiglez.optimalgenerators.utils.UL;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Updater {

    private static Updater instance;

    public Updater() {
        instance = this;
    }


    public Optional<Update> getUpdate(OptimalGenerators plugin) {
        Update update = null;

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                String content = IOUtils.toString(new URL("https://gist.githubusercontent.com/AigleDev/e5108557a55eeef8e109e4a5759cb4e5/raw/447a8aca442fc41c8b84edb8e7b672bc7764b8df/optimalgenerators.json"), Charset.defaultCharset());

                UL.enabling("[Updater] Content is: ");
                UL.enabling(content);

                JsonParser parser = new JsonParser();
                JsonElement json = parser.parse(content);
                JsonArray changes = json.getAsJsonObject().get("changes").getAsJsonArray();

                UL.debug("----------------------------------");
                UL.debug("Version: " + json.getAsJsonObject().get("version").getAsString());
                UL.debug("Tag: " + json.getAsJsonObject().get("tag").getAsString());
                UL.debug("Hotfix: " + json.getAsJsonObject().get("hotfix").getAsBoolean());
                UL.debug("Changes: ");

                for (JsonElement change : changes) {
                    UL.debug("- " + change.getAsString());
                }

                UL.debug("----------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        return Optional.ofNullable(update);
    }



    public static Updater getInstance() {
        return instance;
    }


    public static class Update {

        private String newVersion;
        private String tag;
        private boolean hotfix;
        private List<String> changes;

        public Update(String newVersion, String tag, boolean hotfix, List<String> changes) {
            Validate.notNull(newVersion, "new version may not be null");
            Validate.notNull(tag, "new version tag may not be null");
            Validate.notNull(changes, "changes may not be null");

            this.newVersion = newVersion;
            this.hotfix = hotfix;
            this.changes = changes;
            this.tag = tag;
        }


        public String getNewVersion() {
            return newVersion;
        }

        public String getTag() {
            return tag;
        }

        public boolean isHotfix() {
            return hotfix;
        }

        public List<String> getChanges() {
            return Collections.unmodifiableList(changes);
        }
    }
}
