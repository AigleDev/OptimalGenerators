package me.aiglez.optimalgenerators.objects;

import com.google.gson.annotations.Expose;
import me.aiglez.optimalgenerators.api.GeneratorLocation;
import me.aiglez.optimalgenerators.api.GeneratorType;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Generator implements me.aiglez.optimalgenerators.api.Generator {

    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //
    @Expose
    private UUID uuid, ownerUUID;
    @Expose
    private List<UUID> allowedUUIDs;
    @Expose
    private GeneratorLocation location;
    private boolean running;
    private GeneratorType typeObject;
    private String ownerName;
    @Expose
    private String type;

    @Expose
    private boolean linked;
    @Expose
    private GeneratorLocation chestLocation;

    // -------------------------------------------- //
    // MAIN
    // -------------------------------------------- //
    public Generator(UUID uuid, UUID ownerUUID, String ownerName, List<UUID> allowedUUIDs, GeneratorType type, GeneratorLocation location) {
        Validate.notNull(uuid, "uuid is null");
        Validate.notNull(type, "type is null");
        Validate.notNull(ownerUUID, "owner UUID is null");
        Validate.notNull(location, "location is null");
        Validate.noNullElements(allowedUUIDs, "an uuid is null");

        this.uuid = uuid;
        this.allowedUUIDs = allowedUUIDs;
        this.location = location;
        this.linked = false;
        this.running = false;
        this.typeObject = type;
        this.type = type.getName();
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
    }

    public Generator(UUID uuid, UUID ownerUUID, String ownerName, List<UUID> allowedUUIDs, GeneratorType type, GeneratorLocation location, GeneratorLocation chestLocation) {
        Validate.notNull(uuid, "uuid is null");
        Validate.notNull(type, "type is null");
        Validate.notNull(ownerUUID, "owner UUID is null");
        Validate.notNull(location, "location is null");
        Validate.noNullElements(allowedUUIDs, "an uuid is null");

        this.uuid = uuid;
        this.allowedUUIDs = allowedUUIDs;
        this.location = location;
        this.running = false;

        if(chestLocation != null) {
            this.linked = true;
        }

        this.chestLocation = chestLocation;
        this.typeObject = type;
        this.type = type.getName();
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
    }


    // -------------------------------------------- //
    // METHODS
    // -------------------------------------------- //
    @Override
    public void allow(UUID uuid) {
        Validate.notNull(uuid, "uuid may not be null");

        allowedUUIDs.add(uuid);
    }

    @Override
    public void disallow(UUID uuid) {
        Validate.notNull(uuid, "uuid may not be null");

        allowedUUIDs.remove(uuid);
    }

    @Override
    public boolean isAllowed(UUID uuid) {
        Validate.notNull(uuid, "uuid may not be null");

        return allowedUUIDs.contains(uuid);
    }

    @Override
    public boolean generate() {
        return false;
    }

    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public UUID getGeneratorId() {
        return uuid;
    }

    @Override
    public GeneratorType getType() {
        return typeObject;
    }

    @Override
    public List<UUID> getAllowedUUIDs() {
        return Collections.unmodifiableList(allowedUUIDs);
    }

    @Override
    public GeneratorLocation getLocation() {
        return location;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean state) {
        this.running = state;
    }

    @Override
    public boolean isLinked() {
        return linked;
    }

    @Override
    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    @Override
    public @Nullable GeneratorLocation getChestLocation() {
        return chestLocation;
    }

    @Override
    public void setChestLocation(GeneratorLocation chestLocation) {
        this.chestLocation = chestLocation;
    }

    @Override
    public String toString() {
        return "Generator{" +
                "uuid=" + uuid +
                ", allowedUUIDs=" + allowedUUIDs +
                ", location=" + location +
                ", running=" + running +
                ", typeObject=" + typeObject +
                ", type='" + type + '\'' +
                ", linked=" + linked +
                ", chestLocation=" + (chestLocation == null ? "none" : chestLocation) +
                '}';
    }
}
