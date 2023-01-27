package com.codinglitch.someorigins.mixins;

import com.codinglitch.someorigins.registry.powers.GuidancePower;
import com.codinglitch.someorigins.registry.powers.PhotophillicPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.DisableRegenPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerEntity.class})
public abstract class PlayerEntityMixin extends LivingEntity implements Nameable, CommandOutput {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "canFoodHeal", at = @At("HEAD"), cancellable = true)
    private void someorigins$canFoodHeal(CallbackInfoReturnable<Boolean> info) {
        if (PowerHolderComponent.hasPower(this, PhotophillicPower.class)) {
            if (!this.world.isSkyVisible(this.getBlockPos()))
                info.setReturnValue(false);
        }
    }

    @Inject(method = "shouldDismount", at = @At("RETURN"), cancellable = true)
    private void someorigins$shouldDismount(CallbackInfoReturnable<Boolean> info) {
        if (GuidancePower.canGuide(this.getVehicle()))
            if (PowerHolderComponent.hasPower(this, GuidancePower.class))
                info.setReturnValue(false);
    }
}
