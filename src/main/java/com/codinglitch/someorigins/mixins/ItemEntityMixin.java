package com.codinglitch.someorigins.mixins;

import com.codinglitch.someorigins.SomeOrigins;
import com.codinglitch.someorigins.registry.powers.GuidancePower;
import com.codinglitch.someorigins.registry.powers.PhotophillicPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemEntity.class})
public abstract class ItemEntityMixin extends Entity {
    protected ItemEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void someorigins$onPlayerCollision(PlayerEntity player, CallbackInfo callback) {
        SomeOrigins.info(player);
        if (player.getVehicle() != null) {
            if (PowerHolderComponent.hasPower(player, GuidancePower.class)){
                callback.cancel();
            }
        }
    }
}
