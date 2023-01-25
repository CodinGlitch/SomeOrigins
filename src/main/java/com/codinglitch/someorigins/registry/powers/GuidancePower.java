package com.codinglitch.someorigins.registry.powers;

import com.codinglitch.someorigins.packets.RidingPacketC2S;
import com.codinglitch.someorigins.packets.RidingPacketS2C;
import com.codinglitch.someorigins.registry.SomeOriginsScaleTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class GuidancePower extends Power implements Active {
    private Key key;

    public GuidancePower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
        setTicking(true);
    }

    public static boolean canGuide(Entity entity) {
        return entity instanceof PlayerEntity || entity instanceof MerchantEntity || entity instanceof GolemEntity;
    }

    @Override
    public void onUse() {
        if (entity.world.isClient && entity instanceof PlayerEntity player) {
            if (SomeOriginsScaleTypes.AMORPHOUS_TYPE.getScaleData(player).getScale() <= 0.5f) {
                Entity vehicle = player.getVehicle();
                if (vehicle != null) {
                    RidingPacketC2S.send(vehicle, false);
                    player.stopRiding();
                } else {
                    MinecraftClient client = MinecraftClient.getInstance();
                    Entity target = client.targetedEntity;

                    if (canGuide(target)) {
                        player.startRiding(target);
                        RidingPacketC2S.send(target, true);
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        if (entity.getVehicle() != null) {
            if (entity instanceof ServerPlayerEntity player) {
                if (SomeOriginsScaleTypes.AMORPHOUS_TYPE.getScaleData(player).getScale() > 0.5f) {
                    if (entity.getVehicle() instanceof ServerPlayerEntity playerBeingRidden)
                        RidingPacketS2C.send(player, playerBeingRidden, false);

                    player.stopRiding();
                }
            }

            Entity vehicle = entity.getVehicle();
            if (vehicle instanceof LivingEntity livingVehicle && entity.world.getTime() % 20 == 0) { // first statement should be true anyway but probably better than type casting
                livingVehicle.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1));
            }
        }
        super.tick();
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
