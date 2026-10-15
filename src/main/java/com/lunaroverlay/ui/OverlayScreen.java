package com.lunaroverlay.ui;

import com.lunaroverlay.LunarOverlayClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import java.awt.Color;

public class OverlayScreen extends Screen {
    private int selectedTab = 0;
    private final String[] tabs = {"Visuals", "Render", "Utilities", "Settings"};

    public OverlayScreen() {
        super(Text.literal("Lunar Overlay Menu"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int panelX = this.width / 2 - 200;
        int panelY = this.height / 2 - 130;
        int panelWidth = 400;
        int panelHeight = 260;

        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, new Color(20, 20, 25, 230).getRGB());
        context.fill(panelX, panelY, panelX + 100, panelY + panelHeight, new Color(30, 30, 40, 245).getRGB());

        for (int i = 0; i < tabs.length; i++) {
            int tabY = panelY + 40 + (i * 30);
            boolean selected = (selectedTab == i);
            int tabColor = selected ? new Color(70, 130, 180, 220).getRGB() : new Color(40, 40, 50, 160).getRGB();
            context.fill(panelX + 10, tabY, panelX + 90, tabY + 25, tabColor);
            context.drawText(this.textRenderer, tabs[i], panelX + 20, tabY + 8, 0xFFFFFF, true);
        }

        context.drawText(this.textRenderer, "Lunar Overlay - " + tabs[selectedTab], panelX + 115, panelY + 15, 0x00D2FF, true);

        if (selectedTab == 0) {
            renderVisualsTab(context, panelX + 115, panelY + 45);
        } else {
            context.drawText(this.textRenderer, "Settings & Modules coming soon...", panelX + 115, panelY + 60, 0xAAAAAA, true);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderVisualsTab(DrawContext context, int x, int y) {
        String espStatus = "[ Click to Toggle ] ESP Enabled: " + (LunarOverlayClient.CONFIG.espEnabled ? "ON" : "OFF");
        int espColor = LunarOverlayClient.CONFIG.espEnabled ? 0x55FF55 : 0xFF5555;
        context.drawText(this.textRenderer, espStatus, x, y, espColor, true);

        String hitboxStatus = "[ Click to Toggle ] Hitbox Renderer: " + (LunarOverlayClient.CONFIG.hitboxEnabled ? "ON" : "OFF");
        int hitboxColor = LunarOverlayClient.CONFIG.hitboxEnabled ? 0x55FF55 : 0xFF5555;
        context.drawText(this.textRenderer, hitboxStatus, x, y + 25, hitboxColor, true);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelX = this.width / 2 - 200;
        int panelY = this.height / 2 - 130;

        for (int i = 0; i < tabs.length; i++) {
            int tabY = panelY + 40 + (i * 30);
            if (mouseX >= panelX + 10 && mouseX <= panelX + 90 && mouseY >= tabY && mouseY <= tabY + 25) {
                selectedTab = i;
                return true;
            }
        }

        if (selectedTab == 0) {
            int x = panelX + 115;
            int y = panelY + 45;
            if (mouseX >= x && mouseX <= x + 250 && mouseY >= y && mouseY <= y + 15) {
                LunarOverlayClient.CONFIG.espEnabled = !LunarOverlayClient.CONFIG.espEnabled;
                LunarOverlayClient.CONFIG.save();
                return true;
            }
            if (mouseX >= x && mouseX <= x + 250 && mouseY >= y + 25 && mouseY <= y + 40) {
                LunarOverlayClient.CONFIG.hitboxEnabled = !LunarOverlayClient.CONFIG.hitboxEnabled;
                LunarOverlayClient.CONFIG.save();
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
