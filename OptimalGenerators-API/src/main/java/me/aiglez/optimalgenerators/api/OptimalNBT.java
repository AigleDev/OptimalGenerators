package me.aiglez.optimalgenerators.api;

import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * This is a utility class used for NBT.
 *
 * @author AigleZ
 */
public abstract class OptimalNBT {

    /**
     * Add an NBT tag to an item with given key and value
     *
     * @param item the item stack to set
     * @param value the value to set
     * @param key the key to set
     *
     * @return A new {@link ItemStack} with the new NBT Tag
     */
    public abstract ItemStack setString(ItemStack item, String value, String key);


    /**
     * Add an NBT tag to an item with given key and value
     *
     * @param item the item stack to set
     * @param value the value to set
     * @param key the key to set
     *
     * @return A new {@link ItemStack} with the new NBT Tag
     */
    public abstract ItemStack setInteger(ItemStack item, int value, String key);

    /**
     * Add an NBT tag to an item with given key and value
     *
     * @param item the item stack to set
     * @param value the value to set
     * @param key the key to set
     *
     * @return A new {@link ItemStack} with the new NBT Tag
     */
    public abstract ItemStack setDouble(ItemStack item, double value, String key);

    /**
     * Add an NBT tag to an item with given key and value
     *
     * @param item the item stack to set
     * @param value the value to set
     * @param key the key to set
     *
     * @return A new {@link ItemStack} with the new NBT Tag
     */
    public abstract ItemStack setFloat(ItemStack item, float value, String key);

    /**
     * Add an NBT tag to an item with given key and value
     *
     * @param item the item stack to set
     * @param value the value to set
     * @param key the key to set
     *
     * @return A new {@link ItemStack} with the new NBT Tag
     */
    public abstract ItemStack setShort(ItemStack item, short value, String key);

    /**
     * Get a String from the given item.
     *
     * @param item the item with the tag
     * @param key the key
     *
     * @return An optional (since the String may be null)
     */
    public abstract Optional<String> getString(ItemStack item, String key);

    /**
     * Get an Integer from the given item.
     *
     * @param item the item with the tag
     * @param key the key
     *
     * @return int (-1 if null)
     */
    public abstract int getInteger(ItemStack item, String key);

    /**
     * Get a double from the given item.
     *
     * @param item the item with the tag
     * @param key the key
     *
     * @return a double.
     */
    public abstract double getDouble(ItemStack item, String key);

    /**
     * Get a float from the given item.
     *
     * @param item the item with the tag
     * @param key the key
     *
     * @return a float.
     */
    public abstract float getFloat(ItemStack item, String key);

    /**
     * Get a short from the given item.
     *
     * @param item the item with the tag
     * @param key the key
     *
     * @return a short.
     */
    public abstract short getShort(ItemStack item, String key);


    /**
     * Check if the given item has a TAG with the given key.
     *
     * @param item
     * @param key
     * @return
     */
    public abstract boolean hasTag(ItemStack item, String key);
}
