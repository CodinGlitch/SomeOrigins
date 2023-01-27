package com.codinglitch.someorigins.registry.powers;

import com.codinglitch.someorigins.packets.RidingPacketC2S;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ResourcePower;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class PhotophillicPower extends Power {
    private PowerType<ResourcePower> powerType;

    public PhotophillicPower(PowerType<?> type, LivingEntity entity, PowerType<ResourcePower> powerType) {
        super(type, entity);
        setTicking(true);

        this.powerType = powerType;
    }

    @Override
    public void tick() {
        if (entity.world.isSkyVisible(entity.getBlockPos()) && entity.world.getLightLevel(entity.getBlockPos()) > 10 && entity.world.getTime() % 600 == 0) {
            PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
            ResourcePower power = component.getPower(powerType);

            if (power.getValue() == 7) {
                StatusEffectInstance statusEffect = entity.getStatusEffect(StatusEffects.ABSORPTION);

                if (statusEffect == null) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2000, 0));
                } else {
                    int amplifier = MathHelper.floor(entity.getAbsorptionAmount() / 4f);
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2000 + (amplifier * 1200), Math.min(amplifier, 4)));
                }
            }
        }
        super.tick();
    }
}
