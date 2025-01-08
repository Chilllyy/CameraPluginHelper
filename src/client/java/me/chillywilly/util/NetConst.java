package me.chillywilly.util;

import net.minecraft.util.Identifier;

public class NetConst {
    //Incoming
    public static final Identifier SCREENSHOT_PACKET_ID = Identifier.of("camera", "screenshot");
    public static final Identifier CHECK_FOR_COMPANION_ID = Identifier.of("camera", "companion_syn");

    //Outgoing
    public static final Identifier COMPANION_FOUND_ID = Identifier.of("camera", "companion_ack");
    public static final Identifier SCREENSHOT_TAKEN_ID = Identifier.of("camera", "uploaded");
}