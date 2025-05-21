package me.chillywilly.util.packets.rx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.packet.CustomPayload;

public record CompanionCheckPacket() implements CustomPayload {
    public static final CustomPayload.Id<CompanionCheckPacket> ID = new CustomPayload.Id<>(NetConst.CHECK_FOR_COMPANION_ID);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}