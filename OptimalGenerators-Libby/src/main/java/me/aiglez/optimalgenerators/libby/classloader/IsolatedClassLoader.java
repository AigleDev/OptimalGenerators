package me.aiglez.optimalgenerators.libby.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * This class loader is a simple child of {@code URLClassLoader} that uses
 * the JVM's Extensions Class Loader as the parent instead of the system class
 * loader to provide an unpolluted classpath.
 */
public class IsolatedClassLoader extends URLClassLoader {

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public IsolatedClassLoader(URL... urls) {
        super(requireNonNull(urls, "urls"), ClassLoader.getSystemClassLoader().getParent());
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public void addPath(Path path) {
        try {
            addURL(requireNonNull(path, "path").toUri().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
