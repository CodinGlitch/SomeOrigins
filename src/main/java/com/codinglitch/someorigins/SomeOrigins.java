package com.codinglitch.someorigins;

import com.codinglitch.someorigins.packets.RidingPacketC2S;
import com.codinglitch.someorigins.registry.SomeOriginsScaleTypes;
import com.codinglitch.someorigins.registry.powers.GuidancePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;

public class SomeOrigins extends LoggingModInstance implements ModInitializer {
    static {
        ID = "someorigins";
    }

    @Override
    public void onInitialize() {
        SomeOriginsPowers.initialize();
        SomeOriginsScaleTypes.initialize();

        initializePackets();
        initializeEvents();
    }

    public void initializePackets() {
        ServerPlayNetworking.registerGlobalReceiver(RidingPacketC2S.ID, RidingPacketC2S::recieve);
    }

    private void initializeEvents() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (PowerHolderComponent.hasPower(handler.player, GuidancePower.class) && handler.player.getVehicle() instanceof PlayerEntity) {
                handler.player.dismountVehicle();
            }
        });
    }
}
