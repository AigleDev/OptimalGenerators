package me.aiglez.optimalgenerators.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public final class OptimalNBT extends me.aiglez.optimalgenerators.api.OptimalNBT {

    public OptimalNBT() {
        System.out.println("[OptimalNBT] New instance of OptimalNBT (v1_8_R3) created !");
    }

    public ItemStack setString(ItemStack item, String value, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(value, "value may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound compound = (nmsItemStack.hasTag() ? nmsItemStack.getTag() : new NBTTagCompound());

        compound.set(key, new NBTTagString(value));

        nmsItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public ItemStack setInteger(ItemStack item, int value, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound compound = (nmsItemStack.hasTag()) ? nmsItemStack.getTag() : new NBTTagCompound();
        compound.set(key, new NBTTagInt(value));

        nmsItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public ItemStack setDouble(ItemStack item, double value, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound compound = (nmsItemStack.hasTag()) ? nmsItemStack.getTag() : new NBTTagCompound();
        compound.set(key, new NBTTagDouble(value));

        nmsItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public ItemStack setFloat(ItemStack item, float value, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound compound = (nmsItemStack.hasTag()) ? nmsItemStack.getTag() : new NBTTagCompound();
        compound.set(key, new NBTTagFloat(value));

        nmsItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public ItemStack setShort(ItemStack item, short value, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound compound = (nmsItemStack.hasTag()) ? nmsItemStack.getTag() : new NBTTagCompound();
        compound.set(key, new NBTTagShort(value));

        nmsItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public Optional<String> getString(ItemStack item, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        if(!nmsStack.hasTag()) return Optional.empty();


        NBTTagCompound compound =  nmsStack.getTag();

        if(compound.hasKey(key)) return Optional.ofNullable(compound.getString(key));

        return Optional.empty();
    }

    @Override
    public int getInteger(ItemStack item, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        if(!nmsStack.hasTag()) return -1;

        NBTTagCompound compound =  nmsStack.getTag();

        if(compound.hasKey(key)) return compound.getInt(key);

        return -1;
    }

    @Override
    public double getDouble(ItemStack item, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        if(!nmsStack.hasTag()) return -1;

        NBTTagCompound compound =  nmsStack.getTag();

        if(compound.hasKey(key)) return compound.getDouble(key);

        return -1;
    }

    @Override
    public float getFloat(ItemStack item, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        if(!nmsStack.hasTag()) return -1;

        NBTTagCompound compound =  nmsStack.getTag();

        if(compound.hasKey(key)) return compound.getFloat(key);

        return -1;
    }

    @Override
    public short getShort(ItemStack item, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        if(!nmsStack.hasTag()) return -1;

        NBTTagCompound compound =  nmsStack.getTag();

        if(compound.hasKey(key)) return compound.getShort(key);

        return -1;
    }

    public boolean hasTag(ItemStack item, String key) {
        Validate.notNull(item, "item may not be null");
        Validate.notNull(key, "key may not be null");

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        if(!nmsStack.hasTag()) return false;


        NBTTagCompound compound = nmsStack.getTag();
        if(compound == null) return false;

        return compound.hasKey(key);
    }
}
