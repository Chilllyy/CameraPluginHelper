package me.chillywilly.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class NoPause {
    @Inject(method="onWindowFocusChanged", at = @At("HEAD"), cancellable = true)
    private void windowFocusChange(boolean focused, CallbackInfo ci) {
        if (!focused) {
            ci.cancel();
        }
    }
}
