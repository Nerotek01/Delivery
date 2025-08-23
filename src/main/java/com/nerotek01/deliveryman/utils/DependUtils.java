package com.nerotek01.deliveryman.utils;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.calls.CallBackAPI;
import com.nerotek01.deliveryman.enums.DBType;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

public class DependUtils {

    private final Main plugin;

    public DependUtils(Main plugin) {
        this.plugin = plugin;
    }

    public void loadDepends() {
        File libs = new File(plugin.getDataFolder(), "libs");
        if (!libs.exists()) libs.mkdirs();

        if (plugin.getCm().getDbType().equals(DBType.MYSQL)) {
            loadDependency("HikariCP-3.4.2.jar",
                    "https://repo1.maven.org/maven2/com/zaxxer/HikariCP/3.4.2/HikariCP-3.4.2.jar");

            try {
                Class.forName("org.slf4j.LoggerFactory");
            } catch (ClassNotFoundException e) {
                loadDependency("slf4j-api-1.7.25.jar",
                        "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar");
            }
        }
    }

    private void loadDependency(String name, String url) {
        File file = new File(new File(plugin.getDataFolder(), "libs"), name);
        if (!file.exists()) {
            plugin.sendLogMessage("Downloading dependency §b" + name + "§e...");
            try {
                file.createNewFile();
                downloadDependency(new URL(url), file, progress -> {
                    if (progress >= 100) {
                        plugin.sendLogMessage("§a" + name + " downloaded successfully.");
                        loadJarFile(file);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadJarFile(file);
        }
    }

    private void downloadDependency(URL url, File file, CallBackAPI<Double> callback) {
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream out = new FileOutputStream(file)) {

            URLConnection conn = url.openConnection();
            int size = conn.getContentLength();
            byte[] buffer = new byte[1024];
            double downloaded = 0;

            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                downloaded += count;
                if (size > 0) {
                    callback.done(downloaded / size * 100.0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadJarFile(File jar) {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), jar.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
