package me.aiglez.optimalgenerators.libby.classloader;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A reflection-based wrapper around {@link URLClassLoader} for adding URLs to
 * the classpath.
 */
public class URLClassLoaderHelper {

    private final URLClassLoader classLoader;
    private final Method addURLMethod;

    public URLClassLoaderHelper(URLClassLoader classLoader) {
        this.classLoader = requireNonNull(classLoader, "classLoader");

        try {
            addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURLMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToClasspath(URL url) {
        try {
            addURLMethod.invoke(classLoader, requireNonNull(url, "url"));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToClasspath(Path path) {
        try {
            addToClasspath(requireNonNull(path, "path").toUri().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
