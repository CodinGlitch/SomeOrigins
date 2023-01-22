package com.codinglitch.someorigins.client;

import com.codinglitch.someorigins.packets.RidingPacketS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class SomeOriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initializePackets();
    }

    private void initializePackets() {
        ClientPlayNetworking.registerGlobalReceiver(RidingPacketS2C.ID, RidingPacketS2C::recieve);
    }
}
