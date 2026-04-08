package me.chillywilly.util;

import net.minecraft.resources.Identifier;

public class NetConst {
    //Incoming
    public static final Identifier SCREENSHOT_PACKET_ID = Identifier.fromNamespaceAndPath("camera", "screenshot");
    public static final Identifier CHECK_FOR_COMPANION_ID = Identifier.fromNamespaceAndPath("camera", "companion_syn");

    //Outgoing
    public static final Identifier COMPANION_FOUND_ID = Identifier.fromNamespaceAndPath("camera", "companion_ack");
    public static final Identifier SCREENSHOT_TAKEN_ID = Identifier.fromNamespaceAndPath("camera", "uploaded");
}