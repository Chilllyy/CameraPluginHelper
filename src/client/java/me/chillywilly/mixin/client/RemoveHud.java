package me.chillywilly.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class RemoveHud {
    @Inject(at=@At("HEAD"), method = "render", cancellable = true)
    public void render(CallbackInfo info) {
        info.cancel();
    }
}
