package me.chillywilly.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.chillywilly.CameraPluginHelperClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class RemoveHud {
    @Inject(at=@At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo info) {
        //info.cancel();
        if (CameraPluginHelperClient.taking_screenshot) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.options.hudHidden = true;
            if (client.currentScreen != null) {
                client.currentScreen.close();
            }
        }
    }
}
