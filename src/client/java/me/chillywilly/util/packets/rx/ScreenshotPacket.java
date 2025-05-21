package me.chillywilly.util.packets.rx;

import me.chillywilly.util.NetConst;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ScreenshotPacket(String URL, Integer auth) implements CustomPayload {
    public static final CustomPayload.Id<ScreenshotPacket> ID = new CustomPayload.Id<>(NetConst.SCREENSHOT_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, ScreenshotPacket> CODEC = PacketCodec.tuple(
        PacketCodecs.STRING, ScreenshotPacket::URL, 
        PacketCodecs.INTEGER, ScreenshotPacket::auth, 
        ScreenshotPacket::new
    );



    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}