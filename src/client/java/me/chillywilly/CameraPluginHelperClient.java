package me.chillywilly;

import net.fabricmc.api.ClientModInitializer;

public class CameraPluginHelperClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CameraPluginHelper.LOGGER.info("Hello World, from chilly's second mod!");
	}
}