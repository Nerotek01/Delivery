package com.nerotek01.deliveryman.skins;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.utils.Utils;

import java.io.*;
import java.util.UUID;

public class SkinCache {

    private final String skinPath;
    private final String newLine;

    public SkinCache(Main plugin) {
        this.skinPath = plugin.getDataFolder().getAbsolutePath() + File.separator + "skin" + File.separator;
        this.newLine = System.lineSeparator();
        new File(this.skinPath).mkdirs();
    }

    public SkinProperty loadSkinProperty(UUID uuid) {
        File file = new File(skinPath, uuid.toString());

        if (!file.exists()) {
            SkinProperty sp = Utils.getSkinProperty(uuid);
            saveSkinProperty(uuid, sp);
            return sp;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String value = reader.readLine();
            String signature = reader.readLine();
            return new SkinProperty(value, signature);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveSkinProperty(UUID uuid, SkinProperty sp) {
        File file = new File(skinPath, uuid.toString());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sp.value + newLine + sp.signature);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
