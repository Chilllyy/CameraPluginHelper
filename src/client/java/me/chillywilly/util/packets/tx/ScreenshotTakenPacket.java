package me.chillywilly.util.packets.tx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ScreenshotTakenPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ScreenshotTakenPacket> TYPE = new CustomPacketPayload.Type<>(NetConst.SCREENSHOT_TAKEN_ID);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}