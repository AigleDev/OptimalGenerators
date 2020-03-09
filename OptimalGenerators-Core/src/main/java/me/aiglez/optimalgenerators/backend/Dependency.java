package me.aiglez.optimalgenerators.backend;

import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;

public class Dependency {

    private static Dependency instance;

    public Dependency(){
        instance = this;
    }


    public void downloadGSON(BukkitLibraryManager manager) {
        manager.loadLibrary(Library.builder()
                .groupId("com.google.code.gson")
                .artifactId("gson")
                .version("2.8.3")
                .relocate("com{}google{}gson", "me.aiglez.optimalgenerators.external.gson")
                .build());
    }

    public void downloadIOUtils(BukkitLibraryManager manager) {
        manager.loadLibrary(Library.builder()
                .groupId("commons-io")
                .artifactId("commons-io")
                .version("2.6")
                //org.apache.commons.io
                .relocate("org{}apache{}commons{}io", "me.aiglez.optimalgenerators.external.commons-io")
                .build());
    }

    public static Dependency getInstance() {
        return instance;
    }
}
