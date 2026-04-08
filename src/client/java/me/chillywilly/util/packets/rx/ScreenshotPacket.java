package me.chillywilly.util.packets.rx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ScreenshotPacket(String URL, Integer auth) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ScreenshotPacket> TYPE = new CustomPacketPayload.Type<>(NetConst.SCREENSHOT_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ScreenshotPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ScreenshotPacket::URL,
            ByteBufCodecs.INT, ScreenshotPacket::auth,
            ScreenshotPacket::new
    );



    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}