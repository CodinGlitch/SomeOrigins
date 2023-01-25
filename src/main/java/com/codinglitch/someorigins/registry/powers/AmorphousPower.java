package com.codinglitch.someorigins.registry.powers;

import com.codinglitch.someorigins.packets.RidingPacketC2S;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

public class AmorphousPower extends Power implements Active {
    private Key key;
    private float scale = 1;
    private final ScaleType scaleType;

    public AmorphousPower(PowerType<?> type, LivingEntity entity, ScaleType scaleType) {
        super(type, entity);
        this.scaleType = scaleType;
        setTicking(true);
    }

    @Override
    public void onUse() {
        if (entity.isSneaky()) {
            scale = Math.max(scale - 0.02f, 0.25f);
        } else {
            scale = Math.min(scale + 0.02f, 1.5f);
        }
    }

    @Override
    public void tick() {
        ScaleData data = scaleType.getScaleData(entity);
        if (isActive() && data.getScale() != scale) {
            data.setScale(scale);
        } else if (!isActive() && data.getScale() != 1) {
            data.setScale(1);
        }
        super.tick();
    }

    @Override
    public void onLost() {
        scaleType.getScaleData(entity).setScale(1);
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
