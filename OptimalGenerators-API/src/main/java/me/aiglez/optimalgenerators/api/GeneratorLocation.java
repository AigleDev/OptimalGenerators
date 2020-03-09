package me.aiglez.optimalgenerators.api;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * A little wrapper of {@link Location} to make things easier.
 *
 * @author AigleZ
 */
public interface GeneratorLocation {

    /**
     * Get the Bukkit Location object.
     *
     * @return bukkit location object.
     * @since 1.0
     */
    Location toBukkit();

    /**
     * Drop an item at this location (default offset)
     *
     * @param item item to spawn
     * @since 1.0
     */
    void dropItem(ItemStack item);

    /**
     * Drop an item at this location (default offset)
     *
     * @param item item to spawn
     * @param offset add to Y.
     * @since 1.0
     */
    void dropItem(ItemStack item, double offset);

    /**
     * Check if current location is same as the given one. (x, y, z, world)
     *
     * @param location other location
     * @return true if they are the same.
     * @since 1.0
     */
    boolean equals(GeneratorLocation location);

    /**
     * Clone current GeneratorLocation object.
     *
     * @return clone of current object.
     * @since 1.0
     */
    GeneratorLocation clone();

    /**
     * Get the id of the world that contains this location.
     *
     * @return world id containing this location.
     * @since 1.0
     */
    UUID getWorldId();

    /**
     * Gets the x-coordinate of this location
     *
     * @return x-coordinate
     * @since 1.0
     */
    int getX();

    /**
     * Gets the y-coordinate of this location
     *
     * @return y-coordinate
     * @since 1.0
     */
    int getY();

    /**
     * Gets the z-coordinate of this location
     *
     * @return z-coordinate
     * @since 1.0
     */
    int getZ();

    String toReadable(); // Just for the implementation.
}
