package me.chillywilly.util.packets.tx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.packet.CustomPayload;

public record ScreenshotTakenPacket() implements CustomPayload {
    public static final CustomPayload.Id<ScreenshotTakenPacket> ID = new CustomPayload.Id<>(NetConst.SCREENSHOT_TAKEN_ID);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}