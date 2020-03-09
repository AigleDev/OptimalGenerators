package me.aiglez.optimalgenerators.api;

import me.aiglez.optimalgenerators.api.enums.CommandExecutor;
import me.aiglez.optimalgenerators.api.enums.GenerationType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GeneratorType {

    /**
     * Enable a generator.
     *
     * @param generator generator to enable.
     * @since 1.0
     */
    void enable(Generator generator);

    /**
     * Disable a generator.
     *
     * @param generator generator to disable.
     * @since 1.0
     */
    void disable(Generator generator);

    /**
     * Start all enabled generators.
     *
     * @since 1.0
     */
    void launch();


    /**
     * Get generation type see {@link GenerationType}
     *
     * @return generation type.
     * @since 1.0
     */
    GenerationType getGenerationType();

    /**
     * Modify the generation type.
     *
     * @param type new generation type.
     * @since 1.0
     */
    void setGenerationType(GenerationType type);

    /**
     * Get the name of this type.
     *
     * @return generator type's name.
     * @since 1.0
     */
    String getName();

    /**
     * Get the colored name.
     * Used for GUIs, Holograms...
     *
     * @return fancy name.
     * @since 1.0
     */
    String getFancyName();


    /**
     * Set the fancy name.
     *
     * @param fancyName new fancy name.
     * @since 1.0
     */
    void setFancyName(String fancyName);

    /**
     * Get the max members the owner can allow to access his generator
     *
     * @return max allowed members.
     * @since 1.0
     */
    int getMaxMembers();

    /**
     * Set the max members the owner can allow to access his generator
     *
     * @param maxMembers max allowed members.
     * @since 1.0
     */
    void setMaxMembers(int maxMembers);

    /**
     * Get the max generators count in a chunk / claim / island
     *
     * @return max generators in a chunk.
     * @since 1.0
     */
    int getMaxInChunk();

    /**
     * Set the max generators count in a chunk / claim / island
     *
     * @param maxInChunk max generators in a chunk.
     * @since 1.0
     */
    void setMaxInChunk(int maxInChunk);

    /**
     * Get the delay between every generation.
     *
     * @return generation delay.
     * @since 1.0
     */
    double getGenerationDelay();

    /**
     * Set the generation delay between every generation.
     *
     * @param generationDelay generation delay.
     * @since 1.0
     */
    void setGenerationDelay(double generationDelay);

    /**
     * Get the link cost of this type
     * Will return -1 if the generation type is Command.
     *
     * @return link cost.
     * @since 1.0
     */
    int getLinkCost();

    /**
     * Set the link cost.
     *
     * @param linkCost cost to link.
     * @since 1.0
     */
    void setLinkCost(int linkCost);

    /**
     * Check if the generator type can be linked or not
     * If generation type is command it's false by default.
     *
     * @return link state
     * @since 1.0
     */
    boolean isLinkable();

    /**
     * @deprecated
     * Modify the link state of the generator type
     *
     * @param state link state
     * @since 1.0
     */
    void setLinkable(boolean state);

    /**
     * Get the itemstack generated.
     * May be null if the generation type is Command.
     *
     * @return generated itemstack.
     * @since 1.0
     */
    @Nullable ItemStack getGeneratedItem();

    /**
     * Set the itemstack generated.
     *
     * @param generatedItem new itemstack.
     */
    void setGeneratedItem(ItemStack generatedItem);

    /**
     * Get the command executed.
     * May be null if the generation type is Item.
     *
     * @return executed command.
     * @since 1.0
     */
    @Nullable String getCommand();

    /**
     * Set the executed command.
     *
     * @param command new command.
     * @since 1.0
     */
    void setCommand(String command);

    /**
     * Get the command executor see {@link CommandExecutor}.
     * May be null if the generation type is Item.
     *
     * @return command executor.
     * @since 1.0
     */
    @Nullable CommandExecutor getExecutor();

    /**
     * Set the command executor.
     *
     * @param executor command executor.
     * @since 1.0
     */
    void setExecutor(CommandExecutor executor);


    /**
     * A simple interface to hold hologram data.
     */
    interface HologramData {

        List<String> getLines();

        String getOfflineOwnerField();

        String getOfflineField();

        String getFullChest();

        String getCommandUpdate();

        String getItemUpdate();
    }

    /**
     * A simple interface to generator item data.
     */
    interface ItemData {

        List<String> getLore();

        String getName();

        boolean isGlow();
    }
}
