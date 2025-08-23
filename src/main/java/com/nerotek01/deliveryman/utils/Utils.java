package com.nerotek01.deliveryman.utils;

import com.nerotek01.deliveryman.skins.SkinProperty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.UUID;

public class Utils {

    private static final DecimalFormat df = new DecimalFormat("##.#");

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getOBClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatLocation(Location loc) {
        if (loc == null) return "§cNot set!";
        return loc.getWorld().getName() + ", " +
                df.format(loc.getX()) + ", " +
                df.format(loc.getY()) + ", " +
                df.format(loc.getZ());
    }

    public static Location parseLocation(String str) {
        if (str == null) return null;
        String[] parts = str.split(";");
        if (parts.length < 6) return null;
        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + ";" +
                loc.getX() + ";" +
                loc.getY() + ";" +
                loc.getZ() + ";" +
                loc.getYaw() + ";" +
                loc.getPitch();
    }

    public static SkinProperty getSkinProperty(UUID uuid) {
        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false");
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            conn.addRequestProperty("User-Agent", "Mozilla/5.0");
            conn.addRequestProperty("Cache-Control", "no-cache");
            String json = new Scanner(conn.getInputStream(), "UTF-8").useDelimiter("\\A").next();

            JSONObject obj = (JSONObject) new JSONParser().parse(json);
            JSONArray properties = (JSONArray) obj.get("properties");

            for (Object o : properties) {
                JSONObject property = (JSONObject) o;
                String name = (String) property.get("name");
                String value = (String) property.get("value");
                String signature = (String) property.getOrDefault("signature", null);
                return new SkinProperty(name, value, signature);
            }
        } catch (Exception ignored) {}
        return null;
    }
}
