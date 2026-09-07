package me.almana.logisticsnetworks.network;

import me.almana.logisticsnetworks.LogisticsNetworks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record SetNetworkColorPayload(UUID networkId, int color) implements CustomPacketPayload {

    public static final Type<SetNetworkColorPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(LogisticsNetworks.MOD_ID, "set_network_color"));
    public static final StreamCodec<FriendlyByteBuf, SetNetworkColorPayload> STREAM_CODEC = StreamCodec.of(
            SetNetworkColorPayload::write, SetNetworkColorPayload::read);

    public static SetNetworkColorPayload read(FriendlyByteBuf buffer) {
        return new SetNetworkColorPayload(buffer.readUUID(), buffer.readInt());
    }

    public static void write(FriendlyByteBuf buffer, SetNetworkColorPayload payload) {
        buffer.writeUUID(payload.networkId);
        buffer.writeInt(payload.color);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
