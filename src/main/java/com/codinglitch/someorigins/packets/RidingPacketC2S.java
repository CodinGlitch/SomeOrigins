package com.codinglitch.someorigins.packets;

import com.codinglitch.someorigins.SomeOrigins;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class RidingPacketC2S {
    public static Identifier ID = new Identifier(SomeOrigins.ID, "start_riding_c2s");

    public static void send(Entity entity, boolean toRide) {
        PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
        buffer.writeInt(entity.getId());
        buffer.writeBoolean(toRide);
        ClientPlayNetworking.send(ID, buffer);
    }

    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buffer, PacketSender sender) {
        int id = buffer.readInt();
        boolean toRide = buffer.readBoolean();
        server.execute(() -> {
            Entity entity = player.world.getEntityById(id);
            if (entity != null) {
                if (player.getUuid().equals(entity.getUuid())) return;

                if (toRide) {
                    player.startRiding(entity);
                    if (entity instanceof ServerPlayerEntity playerBeingRidden)
                        RidingPacketS2C.send(playerBeingRidden, player);
                } else {
                    player.stopRiding();
                }
            }
        });
    }
}
