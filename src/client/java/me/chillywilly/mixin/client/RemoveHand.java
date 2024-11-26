package me.chillywilly.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.chillywilly.CameraPluginHelper;
import net.minecraft.client.render.item.HeldItemRenderer;

@Mixin(HeldItemRenderer.class)
public class RemoveHand {
    @Inject(at=@At("HEAD"), cancellable = true, method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V")
    public void render(CallbackInfo info) {
        if (CameraPluginHelper.remove) {
            info.cancel();
        }
    }
}
