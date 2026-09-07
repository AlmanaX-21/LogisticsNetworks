package me.almana.logisticsnetworks.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.almana.logisticsnetworks.LogisticsNetworks;
import me.almana.logisticsnetworks.integration.create.NodeRenderContext;
import me.almana.logisticsnetworks.integration.iris.IrisCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.List;

@EventBusSubscriber(modid = LogisticsNetworks.MOD_ID, value = Dist.CLIENT)
public final class NodeHighlightRenderer {
    private static final float MIN_XZ = -0.501F;
    private static final float MAX_XZ = 0.501F;
    private static final float MIN_Y = -0.001F;
    private static final float MAX_Y = 1.001F;

    private NodeHighlightRenderer() {
    }

    static void queue(NodeRenderContext context, float red, float green, float blue, float alpha, boolean xray) {
        NodeHighlightQueue.add(context.position(), context.rotation(), red, green, blue, alpha, xray);
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (IrisCompat.isRenderingShadowPass()) return;
        var requests = NodeHighlightQueue.drain(event.getStage());
        if (requests.isEmpty()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
        PoseStack poseStack = event.getPoseStack();
        bufferSource.endLastBatch();
        boolean depthTest = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        poseStack.pushPose();
        try {
            if (IrisCompat.isLoaded()) {
                minecraft.getMainRenderTarget().bindWrite(false);
                poseStack.mulPose(event.getModelViewMatrix());
            }
            RenderSystem.enableDepthTest();
            renderHighlights(event, bufferSource, requests, false);
            bufferSource.endBatch(ModRenderTypes.OVERLAY);
            RenderSystem.disableDepthTest();
            renderHighlights(event, bufferSource, requests, true);
            bufferSource.endBatch(ModRenderTypes.OVERLAY_XRAY);
        } finally {
            if (depthTest) RenderSystem.enableDepthTest();
            else RenderSystem.disableDepthTest();
            poseStack.popPose();
        }
    }

    private static void renderHighlights(RenderLevelStageEvent event, MultiBufferSource bufferSource,
            List<NodeHighlightQueue.HighlightRequest> requests, boolean xray) {
        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPosition = event.getCamera().getPosition();
        for (var request : requests) {
            if (request.xray() != xray) continue;
            poseStack.pushPose();
            try {
                Vec3 offset = request.position().subtract(cameraPosition);
                poseStack.translate(offset.x, offset.y, offset.z);
                poseStack.mulPose(request.rotation());
                renderBox(poseStack.last().pose(), bufferSource, request);
            } finally {
                poseStack.popPose();
            }
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide()) NodeHighlightQueue.clear();
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        NodeHighlightQueue.clear();
    }

    private static void renderBox(Matrix4f matrix, MultiBufferSource buffer,
            NodeHighlightQueue.HighlightRequest request) {
        VertexConsumer builder = buffer.getBuffer(request.xray()
                ? ModRenderTypes.OVERLAY_XRAY
                : ModRenderTypes.OVERLAY);
        float red = request.red();
        float green = request.green();
        float blue = request.blue();
        float alpha = request.alpha();

        builder.addVertex(matrix, MIN_XZ, MAX_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MAX_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MAX_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MAX_Y, MIN_XZ).setColor(red, green, blue, alpha);

        builder.addVertex(matrix, MAX_XZ, MIN_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MIN_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MIN_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MIN_Y, MIN_XZ).setColor(red, green, blue, alpha);

        builder.addVertex(matrix, MIN_XZ, MAX_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MIN_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MIN_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MAX_Y, MAX_XZ).setColor(red, green, blue, alpha);

        builder.addVertex(matrix, MAX_XZ, MAX_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MIN_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MIN_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MAX_Y, MIN_XZ).setColor(red, green, blue, alpha);

        builder.addVertex(matrix, MAX_XZ, MAX_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MIN_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MIN_Y, MIN_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MAX_Y, MIN_XZ).setColor(red, green, blue, alpha);

        builder.addVertex(matrix, MIN_XZ, MAX_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MIN_XZ, MIN_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MIN_Y, MAX_XZ).setColor(red, green, blue, alpha);
        builder.addVertex(matrix, MAX_XZ, MAX_Y, MAX_XZ).setColor(red, green, blue, alpha);
    }
}
