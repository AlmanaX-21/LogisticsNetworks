package me.almana.logisticsnetworks.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.almana.logisticsnetworks.data.NetworkColors;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record WrenchColors(int caseRgb, int screenRgb) {

    public static final Codec<WrenchColors> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("case").forGetter(WrenchColors::caseRgb),
            Codec.INT.fieldOf("screen").forGetter(WrenchColors::screenRgb)
    ).apply(instance, WrenchColors::new));
    public static final StreamCodec<FriendlyByteBuf, WrenchColors> STREAM_CODEC = StreamCodec.of(
            WrenchColors::write, WrenchColors::read);

    public WrenchColors {
        caseRgb = NetworkColors.mask(caseRgb);
        screenRgb = NetworkColors.mask(screenRgb);
    }

    private static WrenchColors read(FriendlyByteBuf buffer) {
        return new WrenchColors(buffer.readInt(), buffer.readInt());
    }

    private static void write(FriendlyByteBuf buffer, WrenchColors colors) {
        buffer.writeInt(colors.caseRgb);
        buffer.writeInt(colors.screenRgb);
    }
}
