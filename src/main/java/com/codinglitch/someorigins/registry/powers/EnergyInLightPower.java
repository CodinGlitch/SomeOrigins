package com.codinglitch.someorigins.registry.powers;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ResourcePower;
import net.minecraft.entity.LivingEntity;

public class EnergyInLightPower extends Power {
    private PowerType<ResourcePower> powerType;

    public EnergyInLightPower(PowerType<?> type, LivingEntity entity, PowerType<ResourcePower> powerType) {
        super(type, entity);

        this.setTicking();
        this.powerType = powerType;
    }

    private void increment() {
        PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
        component.getPower(powerType).increment();
        PowerHolderComponent.syncPower(entity, powerType);
    }
    private void decrement() {
        PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
        component.getPower(powerType).decrement();
        PowerHolderComponent.syncPower(entity, powerType);
    }

    @Override
    public void tick() {
        if (!entity.world.isClient) {
            if (entity.world.getLightLevel(entity.getBlockPos()) > 4) {
                // Regain energy
                if (entity.world.getTime() % 100 == 0)
                    increment();
            } else {
                // Lose energy
                if (entity.getVehicle() instanceof LivingEntity) {
                    if (entity.world.getTime() % 120 == 0)
                        decrement();
                } else {
                    if (entity.world.getTime() % 60 == 0)
                        decrement();
                }

            }
        }


        super.tick();
    }
}
