package me.almana.logisticsnetworks.network;

import me.almana.logisticsnetworks.LogisticsNetworks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetWrenchColorsPayload(int handOrdinal, boolean reset, int caseRgb, int screenRgb)
        implements CustomPacketPayload {

    public static final Type<SetWrenchColorsPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(LogisticsNetworks.MOD_ID, "set_wrench_colors"));
    public static final StreamCodec<FriendlyByteBuf, SetWrenchColorsPayload> STREAM_CODEC = StreamCodec.of(
            SetWrenchColorsPayload::write, SetWrenchColorsPayload::read);

    public static SetWrenchColorsPayload read(FriendlyByteBuf buffer) {
        return new SetWrenchColorsPayload(
                buffer.readVarInt(), buffer.readBoolean(), buffer.readInt(), buffer.readInt());
    }

    public static void write(FriendlyByteBuf buffer, SetWrenchColorsPayload payload) {
        buffer.writeVarInt(payload.handOrdinal);
        buffer.writeBoolean(payload.reset);
        buffer.writeInt(payload.caseRgb);
        buffer.writeInt(payload.screenRgb);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
