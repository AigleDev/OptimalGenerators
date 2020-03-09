package me.aiglez.optimalgenerators.api;

public interface OptimalGenerators {

    /**
     * Check if current server is supported (version, gson available, software)
     * @return whether current server is supported;
     */
    boolean isSupported();

    /**
     * Get the plugin version in case.
     * @return optimalgenerators version
     */
    String getVersion();

    /**
     * Get the API version, be aware this will change every time the api is changed (new event, refactor...)
     * @return optimalgenerators-api version
     */
    String getAPIVersion();
}
