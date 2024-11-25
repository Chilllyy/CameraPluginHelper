package me.chillywilly.util;

import me.chillywilly.CameraPluginHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class NetManager {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(NetConst.SCREENSHOT_PACKET_ID, (client, handler, buf, responseSender) -> {
            buf.readByte();
            int auth = buf.readInt();
            CameraPluginHelper.LOGGER.info("Recieved Packet: {}", auth);
        });
    }
}
