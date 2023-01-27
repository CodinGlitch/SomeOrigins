package com.codinglitch.someorigins.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Predicate;

@Mixin({Entity.class})
public class ClientEntityMixin {
    @Inject(at = @At("RETURN"), method = "canHit", cancellable = true)
    private void someorigins$canHit(CallbackInfoReturnable<Boolean> info) {
        Entity self = ((Entity)(Object)this);

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player != null) {
            if (player.getVehicle() != null) {
                if (self.getUuid() == player.getVehicle().getUuid())
                    info.setReturnValue(false);
            } else if (player.getFirstPassenger() != null) {
                if (self.getUuid() == player.getFirstPassenger().getUuid())
                    info.setReturnValue(false);
            }
        }
    }
}
