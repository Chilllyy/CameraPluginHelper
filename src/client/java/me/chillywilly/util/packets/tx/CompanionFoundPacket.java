package me.chillywilly.util.packets.tx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CompanionFoundPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CompanionFoundPacket> TYPE = new CustomPacketPayload.Type<>(NetConst.COMPANION_FOUND_ID);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}