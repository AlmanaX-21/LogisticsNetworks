package me.almana.logisticsnetworks.client.flow;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.almana.logisticsnetworks.ClientConfig;
import me.almana.logisticsnetworks.data.ChannelType;
import net.minecraft.world.phys.Vec3;

final class FlowLines {
    private FlowLines() {
    }

    static void drawBase(FlowBundle bundle, ChannelType type, double now, PoseStack.Pose pose,
                         Vec3 camera, VertexConsumer base) {
        int color = color(type);
        float opacity = (float) ClientConfig.flowLineOpacity;
        for (FlowSegment segment : bundle.segments()) {
            double visible = FlowAnimation.revealed(segment.length(), segment.revealDistance(), now - bundle.startedAt());
            if (visible > 1.0E-8) line(base, pose, camera, segment, 0, visible, color, opacity, opacity);
        }
    }

    static void drawPulses(FlowBundle bundle, ChannelType type, double now, PoseStack.Pose pose,
                           Vec3 camera, VertexConsumer pulse) {
        for (FlowSegment segment : bundle.segments()) {
            double visible = FlowAnimation.revealed(segment.length(), segment.revealDistance(), now - bundle.startedAt());
            pulses(pulse, pose, camera, segment, visible, now - bundle.startedAt(), brighten(color(type)));
        }
    }

    private static void pulses(VertexConsumer buffer, PoseStack.Pose pose, Vec3 camera, FlowSegment segment,
                               double visible, double travelled, int color) {
        double spacing = ClientConfig.flowLinePulseSpacing;
        double length = Math.min(ClientConfig.flowLinePulseLength, spacing);
        double head = FlowAnimation.pulseHead(segment.phaseDistance(), travelled, spacing);
        for (; head < visible + length; head += spacing) {
            double start = Math.max(0, head - length);
            double end = Math.min(visible, head);
            if (end - start < 1.0E-8) continue;
            float first = (float) ((1 - (head - start) / length) * ClientConfig.flowLineOpacity);
            float last = (float) ((1 - (head - end) / length) * ClientConfig.flowLineOpacity);
            line(buffer, pose, camera, segment, start, end, color, first, last);
        }
    }

    private static void line(VertexConsumer buffer, PoseStack.Pose pose, Vec3 camera, FlowSegment segment,
                             double start, double end, int color, float firstAlpha, float lastAlpha) {
        Vec3 direction = segment.end().subtract(segment.start()).normalize();
        Vec3 origin = segment.start().subtract(camera);
        vertex(buffer, pose, origin.add(direction.scale(start)), direction, color, firstAlpha);
        vertex(buffer, pose, origin.add(direction.scale(end)), direction, color, lastAlpha);
    }

    private static void vertex(VertexConsumer buffer, PoseStack.Pose pose, Vec3 point, Vec3 normal, int color, float alpha) {
        buffer.addVertex(pose, (float) point.x, (float) point.y, (float) point.z)
                .setColor((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F, (color & 255) / 255F, alpha)
                .setNormal(pose, (float) normal.x, (float) normal.y, (float) normal.z);
    }

    private static int brighten(int color) {
        int red = (color >> 16 & 255) + (255 - (color >> 16 & 255)) / 2;
        int green = (color >> 8 & 255) + (255 - (color >> 8 & 255)) / 2;
        int blue = (color & 255) + (255 - (color & 255)) / 2;
        return red << 16 | green << 8 | blue;
    }

    private static int color(ChannelType type) {
        return switch (type) {
            case ITEM -> 0xB87D1F;
            case FLUID -> 0x1C94AC;
            case ENERGY -> 0xB43D3D;
            case CHEMICAL -> 0x2E944F;
            case SOURCE -> 0x7D49B8;
        };
    }
}
