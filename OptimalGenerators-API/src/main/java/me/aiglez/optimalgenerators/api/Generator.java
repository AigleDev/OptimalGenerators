package me.aiglez.optimalgenerators.api;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Main object ;)
 *
 * @author AigleZ
 */
public interface Generator {

    /**
     * Give a player access to the generator.
     *
     * @param uuid target's unique id.
     * @since 1.0
     */
    void allow(UUID uuid);

    /**
     * Revoke access of a player to the generator.
     *
     * @param uuid target's unique id.
     * @since 1.0
     */
    void disallow(UUID uuid);

    /**
     * Check if an uuid is allowed to access the generator
     *
     * @param uuid target's unique id.
     * @return true if the player is allowed, false if not.
     * @since 1.0
     */
    boolean isAllowed(UUID uuid);

    /**
     * Generate (execute a command / spawn an item)
     *
     * @return true if no error occurred during the generation.
     * @since 1.0
     */
    boolean generate();

    /**
     * Get the Generator owner UUID.
     *
     * @return owner's id.
     * @since 1.0
     */
    UUID getOwnerUUID();

    /**
     * Get the Generator owner name.
     *
     * @return owner's name.
     * @since 1.0
     */
    String getOwnerName();

    /**
     * Get the Generator unique id.
     *
     * @return generator's id.
     * @since 1.0
     */
    UUID getGeneratorId();

    /**
     * Get the type of the generator.
     *
     * @return generator's type.
     * @since 1.0
     */
    GeneratorType getType();

    /**
     * Get the list of allowed players uuid.
     *
     * @return unmodifiable list of allowed players uuid.
     * @since 1.0
     */
    List<UUID> getAllowedUUIDs();

    /**
     * Get this generator location see {@link GeneratorLocation}.
     *
     * @return generator's location.
     * @since 1.0
     */
    GeneratorLocation getLocation();

    /**
     * Check if the generator is running or not
     *
     * @return generator's state (true if on, false if off)
     * @since 1.0
     */
    boolean isRunning();

    /**
     * Modify the generator's state.
     *
     * @param state new state.
     * @since 1.0
     */
    void setRunning(boolean state);

    /**
     * Check if the generator is linked to a chest or not.
     *
     * @return link state
     * @since 1.0
     */
    boolean isLinked();

    /**
     * Modify link state of the generator.
     *
     * @param linked new link state.
     * @since 1.0
     */
    void setLinked(boolean linked);

    /**
     * Get the linked chest location.
     * Location may be null if the generator isn't linked to a chest.
     *
     * @return linked chest location.
     * @since 1.0
     */
    @Nullable GeneratorLocation getChestLocation();

    /**
     * Set the linked chest location.
     *
     * @param location new location.
     * @since 1.0
     */
    void setChestLocation(GeneratorLocation location);
}
