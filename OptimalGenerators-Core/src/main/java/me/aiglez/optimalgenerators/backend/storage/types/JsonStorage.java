package me.aiglez.optimalgenerators.backend.storage.types;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.aiglez.optimalgenerators.backend.storage.Storage;

public class JsonStorage implements Storage {

    private static Gson gson;

    public JsonStorage() {
        gson = new GsonBuilder().setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
    }

}
