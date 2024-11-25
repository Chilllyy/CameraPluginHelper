package me.chillywilly.util;

import me.chillywilly.CameraPluginHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class NetManager {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(NetConst.SCREENSHOT_PACKET_ID, (client, handler, buf, responseSender) -> {
            buf.readByte();
            int auth = buf.readInt();
            CameraPluginHelper.LOGGER.info("Received screenshot packed, Auth ID {}", auth);
            //TODO screenshot & return SCREENSHOT_TAKEN_ID packet
        });

        ClientPlayNetworking.registerGlobalReceiver(NetConst.CHECK_FOR_COMPANION_ID, (client, handler, buf, responseSender) -> {
            CameraPluginHelper.LOGGER.info("Received companion check request, responding now");

            //sends response packet so the server knows that the client has the mod
            ClientPlayNetworking.send(NetConst.COMPANION_FOUND_ID, PacketByteBufs.empty());
        });
    }
}
