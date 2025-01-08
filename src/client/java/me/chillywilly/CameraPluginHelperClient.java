package me.chillywilly;

import me.chillywilly.util.NetManager;
import net.fabricmc.api.ClientModInitializer;

public class CameraPluginHelperClient implements ClientModInitializer {
	public static boolean taking_screenshot = false;
	@Override
	public void onInitializeClient() {
		NetManager.init();
	}
}