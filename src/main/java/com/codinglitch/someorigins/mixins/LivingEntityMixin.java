package com.codinglitch.someorigins.mixins;

import com.codinglitch.someorigins.registry.powers.GuidancePower;
import com.codinglitch.someorigins.registry.powers.PhotophillicPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0)
    private float someorigins$damage(float amount) {
        Entity passenger;
        if ((passenger = this.getFirstPassenger()) != null ) {
            if (passenger instanceof LivingEntity livingPassenger) {
                if (PowerHolderComponent.hasPower(livingPassenger, GuidancePower.class)) {
                    amount *= 0.75f;
                    livingPassenger.damage(new EntityDamageSource("protecting_other", this), amount * 0.25f);
                }
            }
        }
        return amount;
    }
}
