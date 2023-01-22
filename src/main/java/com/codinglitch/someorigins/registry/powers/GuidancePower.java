package com.codinglitch.someorigins.registry.powers;

import com.codinglitch.someorigins.packets.RidingPacketC2S;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;

public class GuidancePower extends Power implements Active {
    private Key key;

    public GuidancePower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @Override
    public void onUse() {
        if (entity.world.isClient && entity instanceof ClientPlayerEntity player) {
            if (player.getVehicle() != null) {
                player.stopRiding();
                RidingPacketC2S.send(player.getVehicle(), false);
            } else {
                MinecraftClient client = MinecraftClient.getInstance();
                Entity target = client.targetedEntity;

                if (target instanceof PlayerEntity || target instanceof MerchantEntity || target instanceof GolemEntity) {
                    player.startRiding(target);
                    RidingPacketC2S.send(target, true);
                }
            }
        }
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
