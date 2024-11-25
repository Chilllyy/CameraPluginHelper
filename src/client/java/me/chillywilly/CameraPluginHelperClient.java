package me.chillywilly;

import me.chillywilly.util.NetManager;
import net.fabricmc.api.ClientModInitializer;

public class CameraPluginHelperClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		NetManager.init();
	}


}