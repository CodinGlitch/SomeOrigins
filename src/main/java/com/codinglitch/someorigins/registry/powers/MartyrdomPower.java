package com.codinglitch.someorigins.registry.powers;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

public class MartyrdomPower extends Power implements Active {
    private Key key;
    private PowerType<ResourcePower> powerType;

    public MartyrdomPower(PowerType<?> type, LivingEntity entity, PowerType<ResourcePower> powerType) {
        super(type, entity);

        this.powerType = powerType;
    }

    @Override
    public void onUse() {
        if (entity.getVehicle() instanceof LivingEntity vehicle && !entity.world.isClient) {
            PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
            ResourcePower power = component.getPower(powerType);
            if (power.getValue() > 1) {
                power.setValue(power.getValue() - 2);
                PowerHolderComponent.syncPower(entity, powerType);

                vehicle.heal(vehicle.getMaxHealth() * 0.25f);
            } else {
                float amount = entity.getMaxHealth() * (0.2f - power.getValue() * 0.1f);
                power.setValue(0);

                vehicle.heal(amount);
                entity.damage(new EntityDamageSource("protecting_other", vehicle), amount);
            }
        }
    }

    @Override
    public void onLost() {

    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }
}
