package me.aiglez.optimalgenerators.libby;

import me.aiglez.optimalgenerators.libby.classloader.URLClassLoaderHelper;
import me.aiglez.optimalgenerators.libby.relocation.Relocation;
import me.aiglez.optimalgenerators.libby.relocation.RelocationHelper;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class DependencyManager {

    // -------------------------------------------- //
    // FIELDS
    // -------------------------------------------- //
    private final Logger logger;
    private boolean printErrors;

    private final Dependency GSON_LIBRARY;
    private final Dependency IO_LIBRARY;

    protected final Path saveDirectory;
    private final URLClassLoaderHelper classLoader;
    private final List<String> repositories = new LinkedList<>();
    private RelocationHelper relocator;

    // -------------------------------------------- //
    // MAIN
    // -------------------------------------------- //
    public DependencyManager(Plugin plugin, Path dataDirectory) {
        saveDirectory = requireNonNull(dataDirectory, "dataDirectory").toAbsolutePath().resolve("lib");
        this.logger = plugin.getLogger();

        classLoader = new URLClassLoaderHelper((URLClassLoader) plugin.getClass().getClassLoader());

        GSON_LIBRARY = Dependency.builder()
                .groupId("com.google.code.gson")
                .artifactId("gson")
                .version("2.8.3")
                .relocate("com{}google{}gson", "me.aiglez.optimalgenerators.external.gson")
                .build();

        IO_LIBRARY = Dependency.builder()
                .groupId("commons-io")
                .artifactId("commons-io")
                .version("2.6")
                //org.apache.commons.io
                .relocate("org{}apache{}commons{}io", "me.aiglez.optimalgenerators.external.commons-io")
                .build();
    }

    // -------------------------------------------- //
    // CONFIG
    // -------------------------------------------- //
    public boolean printErrors() {
        return printErrors;
    }

    public void setPrintErrors(boolean printErrors) {
        this.printErrors = printErrors;
    }
    // -------------------------------------------- //
    // REPOSITORIES
    // -------------------------------------------- //
    public Collection<String> getRepositories() {
        List<String> urls;
        synchronized (repositories) {
            urls = new LinkedList<>(repositories);
        }

        return Collections.unmodifiableList(urls);
    }

    public void addRepository(String url) {
        String repo = requireNonNull(url, "url").endsWith("/") ? url : url + '/';
        synchronized (repositories) {
            repositories.add(repo);
        }
    }

    public void addMavenLocal() {
        addRepository(Paths.get(System.getProperty("user.home")).resolve(".m2/repository").toUri().toString());
    }

    public void addMavenCentral() {
        addRepository("https://repo1.maven.org/maven2/");
    }

    public void addSonatype() {
        addRepository("https://oss.sonatype.org/content/groups/public/");
    }

    public void addJCenter() {
        addRepository("https://jcenter.bintray.com/");
    }

    public void addJitPack() {
        addRepository("https://jitpack.io/");
    }


    public void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }

    // -------------------------------------------- //
    // DEPENDENCIES
    // -------------------------------------------- //
    private Collection<String> resolveDependency(Dependency dependency) {
        List<String> urls = new LinkedList<>(requireNonNull(dependency, "dependency").getUrls());
        for (String repository : getRepositories()) {
            urls.add(repository + dependency.getPath());
        }

        return Collections.unmodifiableList(urls);
    }

    private byte[] downloadDependency(String name, String url) {
        try {
            URLConnection connection = new URL(requireNonNull(url, "url")).openConnection();

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("User-Agent", "libby/1.0");

            try (InputStream in = connection.getInputStream()) {
                long start = System.currentTimeMillis();

                int len;
                byte[] buf = new byte[8192];
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                try {
                    while ((len = in.read(buf)) != -1) {
                        out.write(buf, 0, len);
                    }
                } catch (SocketTimeoutException e) {
                    logger.warning("[Dependency] Download timed out: " + connection.getURL());
                    return null;
                }

                if(!(name.equalsIgnoreCase("asm-commons") || name.equalsIgnoreCase("asm") || name.equalsIgnoreCase("jar-relocator"))) {
                    logger.info("[Dependency] Successfully downloaded a dependency " + name + " (took: " + (System.currentTimeMillis() - start) + "ms");
                }

                return out.toByteArray();
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);

        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                if(printErrors) logger.warning("[Dependency] File not found: " + url);
            } else if (e instanceof SocketTimeoutException) {
                if(printErrors) logger.warning("[Dependency] Connect timed out: " + url);
            } else if (e instanceof UnknownHostException) {
                if(printErrors) logger.warning("[Dependency] Unknown host: " + url);
            } else {
                if(printErrors) logger.warning("[Dependency] Unexpected IOException " + e.getMessage());
            }

            return null;
        }
    }

    public Path downloadDependency(Dependency dependency) {
        Path file = saveDirectory.resolve(requireNonNull(dependency, "dependency").getPath());
        if (Files.exists(file)) {
            return file;
        }

        Collection<String> urls = resolveDependency(dependency);
        if (urls.isEmpty()) {
            throw new RuntimeException("Dependency '" + dependency + "' couldn't be resolved, add a repository");
        }

        MessageDigest md = null;
        if (dependency.hasChecksum()) {
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        Path out = file.resolveSibling(file.getFileName() + ".tmp");
        out.toFile().deleteOnExit();

        try {
            Files.createDirectories(file.getParent());

            for (String url : urls) {
                byte[] bytes = downloadDependency(dependency.getArtifactId(), url);
                if (bytes == null) {
                    continue;
                }

                if (md != null) {
                    byte[] checksum = md.digest(bytes);
                    if (!Arrays.equals(checksum, dependency.getChecksum())) {
                        logger.warning("*** INVALID CHECKSUM ***");
                        logger.warning(" Library :  " + dependency);
                        logger.warning(" URL :  " + url);
                        logger.warning(" Expected :  " + Base64.getEncoder().encodeToString(dependency.getChecksum()));
                        logger.warning(" Actual :  " + Base64.getEncoder().encodeToString(checksum));
                        continue;
                    }
                }

                Files.write(out, bytes);
                Files.move(out, file);

                return file;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            try {
                Files.deleteIfExists(out);
            } catch (IOException ignored) {
            }
        }

        throw new RuntimeException("Failed to download library '" + dependency.getArtifactId() + "'");
    }

    private Path relocate(Path in, String out, Collection<Relocation> relocations) {
        requireNonNull(in, "in");
        requireNonNull(out, "out");
        requireNonNull(relocations, "relocations");

        Path file = saveDirectory.resolve(out);
        if (Files.exists(file)) {
            return file;
        }

        Path tmpOut = file.resolveSibling(file.getFileName() + ".tmp");
        tmpOut.toFile().deleteOnExit();

        synchronized (this) {
            if (relocator == null) {
                relocator = new RelocationHelper(this);
            }
        }

        try {
            relocator.relocate(in, tmpOut, relocations);
            Files.move(tmpOut, file);

            logger.info("Relocations applied to " + saveDirectory.getParent().relativize(in).getFileName());

            return file;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            try {
                Files.deleteIfExists(tmpOut);
            } catch (IOException ignored) {
            }
        }
    }

    public void loadGSON() {
        loadDependency(GSON_LIBRARY);
    }

    public void loadIOCommons() {
        loadDependency(IO_LIBRARY);
    }

    private void loadDependency(Dependency dependency) {
        Path file = downloadDependency(Objects.requireNonNull(dependency, "dependency"));
        if (dependency.hasRelocations()) {
            file = relocate(file, dependency.getRelocatedPath(), dependency.getRelocations());
        }

        addToClasspath(file);
    }
}
