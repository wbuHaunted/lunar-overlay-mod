package com.lunaroverlay.render;

import com.lunaroverlay.LunarOverlayClient;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class WorldRenderHandler {
    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (!LunarOverlayClient.CONFIG.espEnabled && !LunarOverlayClient.CONFIG.hitboxEnabled) return;

            MatrixStack matrices = context.matrixStack();
            Vec3d cameraPos = context.camera().getPos();
            VertexConsumerProvider.Immediate vertexConsumers = context.consumers();

            if (vertexConsumers == null) return;

            for (Entity entity : context.world().getEntities()) {
                if (entity == context.camera().getFocusedEntity()) continue;

                if (entity instanceof PlayerEntity && LunarOverlayClient.CONFIG.playerEsp) {
                    renderEntityBox(matrices, vertexConsumers, entity, cameraPos);
                }
            }
        });
    }

    private static void renderEntityBox(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, Vec3d cameraPos) {
        Box box = entity.getBoundingBox().offset(-cameraPos.x, -cameraPos.y, -cameraPos.z);
    }
}
