package me.aiglez.optimalgenerators.libby.relocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static java.util.Objects.requireNonNull;

public class Relocation {

    private final String pattern;
    private final String relocatedPattern;
    private final Collection<String> includes;
    private final Collection<String> excludes;

    public Relocation(String pattern, String relocatedPattern, Collection<String> includes, Collection<String> excludes) {
        this.pattern = requireNonNull(pattern, "pattern").replace("{}", ".");
        this.relocatedPattern = requireNonNull(relocatedPattern, "relocatedPattern").replace("{}", ".");
        this.includes = includes != null ? Collections.unmodifiableList(new LinkedList<>(includes)) : Collections.emptyList();
        this.excludes = excludes != null ? Collections.unmodifiableList(new LinkedList<>(excludes)) : Collections.emptyList();
    }

    /**
     * Creates a new relocation with empty includes and excludes.
     *
     * @param pattern          search pattern
     * @param relocatedPattern replacement pattern
     */
    public Relocation(String pattern, String relocatedPattern) {
        this(pattern, relocatedPattern, null, null);
    }

    /**
     * Gets the search pattern.
     *
     * @return pattern to search
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Gets the replacement pattern.
     *
     * @return pattern to replace with
     */
    public String getRelocatedPattern() {
        return relocatedPattern;
    }

    /**
     * Gets included classes and resources.
     *
     * @return classes and resources to include
     */
    public Collection<String> getIncludes() {
        return includes;
    }

    /**
     * Gets excluded classes and resources.
     *
     * @return classes and resources to exclude
     */
    public Collection<String> getExcludes() {
        return excludes;
    }

    /**
     * Creates a new relocation builder.
     *
     * @return new relocation builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Provides an alternative method of creating a {@link Relocation}. This
     * builder may be more intuitive for configuring relocations that also have
     * any includes or excludes.
     */
    public static class Builder {

        private String pattern;
        private String relocatedPattern;
        private final Collection<String> includes = new LinkedList<>();
        private final Collection<String> excludes = new LinkedList<>();

        public Builder pattern(String pattern) {
            this.pattern = requireNonNull(pattern, "pattern");
            return this;
        }

        public Builder relocatedPattern(String relocatedPattern) {
            this.relocatedPattern = requireNonNull(relocatedPattern, "relocatedPattern");
            return this;
        }

        public Builder include(String include) {
            includes.add(requireNonNull(include, "include"));
            return this;
        }

        public Builder exclude(String exclude) {
            excludes.add(requireNonNull(exclude, "exclude"));
            return this;
        }

        public Relocation build() {
            return new Relocation(pattern, relocatedPattern, includes, excludes);
        }
    }
}
