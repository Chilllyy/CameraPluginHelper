package me.chillywilly.util.packets.rx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CompanionCheckPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CompanionCheckPacket> TYPE = new CustomPacketPayload.Type<>(NetConst.CHECK_FOR_COMPANION_ID);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}