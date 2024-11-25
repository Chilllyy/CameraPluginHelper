package me.chillywilly;

import me.chillywilly.util.NetManager;
import net.fabricmc.api.ClientModInitializer;

public class CameraPluginHelperClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CameraPluginHelper.LOGGER.info("Hello World, from chilly's second mod!");
		NetManager.init();
	}
}