package me.chillywilly.util.packets.tx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.packet.CustomPayload;

public record CompanionFoundPacket() implements CustomPayload {
    public static final CustomPayload.Id<CompanionFoundPacket> ID = new CustomPayload.Id<>(NetConst.COMPANION_FOUND_ID);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}