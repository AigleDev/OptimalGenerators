package me.aiglez.optimalgenerators.objects;

import me.aiglez.optimalgenerators.OptimalGenerators;
import me.aiglez.optimalgenerators.api.Generator;
import me.aiglez.optimalgenerators.api.enums.CommandExecutor;
import me.aiglez.optimalgenerators.api.enums.GenerationType;
import me.aiglez.optimalgenerators.utils.UL;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GeneratorType implements me.aiglez.optimalgenerators.api.GeneratorType {

    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //
    private String name, fancyName;
    private int linkCost, allowedMembers, maxInChunk;
    private double delay;
    private GenerationType type;
    private HologramData hologramData;
    private ItemData itemData;

    // Item Generator
    private boolean linkable;
    private ItemStack generatedItem;

    // Command Generator
    private String command;
    private CommandExecutor executor;

    private int taskId;
    private List<Generator> activeGenerators = new ArrayList<>();

    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //
    public GeneratorType(String name) {
        Validate.notNull(name, "name may not be null");
        this.name = name;
    }


    // -------------------------------------------- //
    // GETTERS & SETTERS
    // -------------------------------------------- //

    @Override
    public void enable(Generator generator) {
        Validate.notNull(generator, "generator may not be null");
        if(generator.isRunning()) {
            UL.error("The generator with id '" + generator.getGeneratorId() + "' is already running.");
            return;
        }

        activeGenerators.add(generator);
    }

    @Override
    public void disable(Generator generator) {
        Validate.notNull(generator, "generator may not be null");
        if(!generator.isRunning()) {
            UL.error("The generator with id '" + generator.getGeneratorId() + "' is NOT running.");
            return;
        }

        activeGenerators.remove(generator);
    }

    @Override
    public void launch() {
        final OptimalGenerators instance = OptimalGenerators.instance();

        UL.debug("[Generate] Start generating a total of " + activeGenerators.size() + " generator(s).");
        taskId = instance.getServer().getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            activeGenerators.forEach(Generator::generate);
        }, 20L, (long) delay);
    }

    @Override
    public GenerationType getGenerationType() {
        return type;
    }

    @Override
    public void setGenerationType(GenerationType type) {
        Validate.notNull(type, "generation type may not be null");
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFancyName() {
        return fancyName;
    }

    @Override
    public void setFancyName(String fancyName) {
        Validate.notNull(fancyName, "fancy name may not be null");
        this.fancyName = fancyName;
    }

    @Override
    public int getMaxMembers() {
        return allowedMembers;
    }

    @Override
    public void setMaxMembers(int maxMembers) {
        this.allowedMembers = maxMembers;
    }

    @Override
    public int getMaxInChunk() {
        return maxInChunk;
    }

    @Override
    public void setMaxInChunk(int maxInChunk) {
        this.maxInChunk = maxInChunk;
    }

    @Override
    public double getGenerationDelay() {
        return delay;
    }

    @Override
    public void setGenerationDelay(double generationDelay) {
        if(generationDelay < 20) {
            throw new IllegalArgumentException("generation delay mush not be inferior to 20 ticks (1 second) MAY CAUSE LAG!");
        }

        this.delay = generationDelay;
    }

    @Override
    public int getLinkCost() {
        return linkCost;
    }

    @Override
    public void setLinkCost(int linkCost) {
        this.linkCost = linkCost;
    }

    @Override
    public boolean isLinkable() {
        return linkable;
    }

    @Override
    public void setLinkable(boolean state) {
        this.linkable = state;
    }

    @Override
    public ItemStack getGeneratedItem() {
        return generatedItem;
    }

    @Override
    public void setGeneratedItem(ItemStack generatedItem) {
        Validate.notNull(generatedItem, "generated item may not be null");

        this.generatedItem = generatedItem;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void setCommand(String command) {
        Validate.notNull(generatedItem, "command may not be null");

        this.command = command;
    }

    @Override
    public CommandExecutor getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(CommandExecutor executor) {
        Validate.notNull(executor, "executor may not be null");

        this.executor = executor;
    }


    public static class HologramData implements me.aiglez.optimalgenerators.api.GeneratorType.HologramData {

        private List<String> lines;
        private String offlineField, offlineOwnerField, fullChest, commandUpdate, itemUpdate;

        public HologramData(List<String> lines, String offlineField, String offlineOwnerField, String fullChest, String commandUpdate, String itemUpdate) {
            Validate.notNull(lines);
            Validate.notNull(offlineField);
            Validate.notNull(offlineOwnerField);
            Validate.notNull(fullChest);
            Validate.notNull(commandUpdate);
            Validate.notNull(itemUpdate);

            this.lines = lines;
            this.offlineField = offlineField;
            this.offlineOwnerField = offlineOwnerField;
            this.fullChest = fullChest;
            this.commandUpdate = commandUpdate;
            this.itemUpdate = itemUpdate;
        }

        @Override
        public List<String> getLines() {
            return lines;
        }

        @Override
        public String getOfflineOwnerField() {
            return offlineOwnerField;
        }

        @Override
        public String getOfflineField() {
            return offlineField;
        }

        @Override
        public String getFullChest() {
            return fullChest;
        }

        @Override
        public String getCommandUpdate() {
            return commandUpdate;
        }

        @Override
        public String getItemUpdate() {
            return itemUpdate;
        }
    }


}
