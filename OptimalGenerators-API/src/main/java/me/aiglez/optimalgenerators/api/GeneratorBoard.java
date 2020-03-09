package me.aiglez.optimalgenerators.api;

import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;

public interface GeneratorBoard {

    Optional<Generator> getGeneratorAtLocation(GeneratorLocation generatorLocation);

    Optional<Generator> getGeneratorAtLocation(Location location);

    Collection<Generator> getGeneratorsAtChunk(Chunk chunk);

    boolean isGeneratorAtLocation(GeneratorLocation generatorLocation);

    boolean registerGenerator(Generator generator);

    boolean unregisterGenerator();
}
