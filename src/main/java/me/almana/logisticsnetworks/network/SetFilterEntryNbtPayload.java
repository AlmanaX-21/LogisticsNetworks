package me.almana.logisticsnetworks.network;

import me.almana.logisticsnetworks.Logisticsnetworks;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetFilterEntryNbtPayload(int slot, String path, boolean remove, String rawValue)
        implements CustomPacketPayload {

    public SetFilterEntryNbtPayload(int slot, String path, boolean remove) {
        this(slot, path, remove, "");
    }

    public static final Type<SetFilterEntryNbtPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Logisticsnetworks.MOD_ID, "set_filter_entry_nbt"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetFilterEntryNbtPayload> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.VAR_INT,
                    SetFilterEntryNbtPayload::slot,
                    ByteBufCodecs.STRING_UTF8,
                    SetFilterEntryNbtPayload::path,
                    ByteBufCodecs.BOOL,
                    SetFilterEntryNbtPayload::remove,
                    ByteBufCodecs.STRING_UTF8,
                    SetFilterEntryNbtPayload::rawValue,
                    SetFilterEntryNbtPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
