package me.aiglez.optimalgenerators.objects;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GeneratorLocation implements me.aiglez.optimalgenerators.api.GeneratorLocation {

    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //
    @Expose
    private UUID worldId; // for json :/
    @Expose
    private int x, y, z;

    // -------------------------------------------- //
    // MAIN
    // -------------------------------------------- //
    public GeneratorLocation(UUID worldId, int x, int y, int z) {
        Validate.notNull(worldId, "world id may not be null");

        this.worldId = worldId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // -------------------------------------------- //
    // METHODS
    // -------------------------------------------- //

    @Override
    public Location toBukkit() {
        return new Location(Bukkit.getWorld(worldId), x, y, z);
    }

    @Override
    public void dropItem(ItemStack item) {
        Validate.notNull(item, "item may not be null");
        dropItem(item, 0.75D);
    }

    @Override
    public void dropItem(ItemStack item, double offset) {
        Validate.notNull(item, "item may not be null");
        if(offset <= 0) offset = 0.75D;

        toBukkit().getWorld().dropItemNaturally(toBukkit().clone().add(0D, offset, 0D), item);
    }

    @Override
    public boolean equals(me.aiglez.optimalgenerators.api.GeneratorLocation location) {
        Validate.notNull(location, "location may not be null");

        return worldId.equals(location.getWorldId()) && getX() == location.getX() && getY() == location.getY() && getZ() == location.getZ();
    }

    @Override
    public me.aiglez.optimalgenerators.api.GeneratorLocation clone() {
        return new GeneratorLocation(worldId, x, y, z);
    }

    @Override
    public UUID getWorldId() {
        return worldId;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public String toReadable() {
        return "x: " + x + ", y: " + y + ", z: " + z;
    }
}
