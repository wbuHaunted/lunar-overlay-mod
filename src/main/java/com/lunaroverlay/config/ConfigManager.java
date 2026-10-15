package com.lunaroverlay.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "lunar_overlay_mod.json");

    public boolean espEnabled = true;
    public boolean playerEsp = true;
    public boolean hostileEsp = true;
    public boolean passiveEsp = true;
    public boolean hitboxEnabled = false;
    public boolean tracersEnabled = false;
    public float boxThickness = 2.0f;
    public float opacity = 0.6f;

    public void load() {
        if (!configFile.exists()) {
            save();
            return;
        }
        try (FileReader reader = new FileReader(configFile)) {
            ConfigManager loaded = GSON.fromJson(reader, ConfigManager.class);
            if (loaded != null) {
                this.espEnabled = loaded.espEnabled;
                this.playerEsp = loaded.playerEsp;
                this.hostileEsp = loaded.hostileEsp;
                this.passiveEsp = loaded.passiveEsp;
                this.hitboxEnabled = loaded.hitboxEnabled;
                this.tracersEnabled = loaded.tracersEnabled;
                this.boxThickness = loaded.boxThickness;
                this.opacity = loaded.opacity;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
