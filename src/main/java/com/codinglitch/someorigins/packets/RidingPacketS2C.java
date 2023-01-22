package com.codinglitch.someorigins.packets;

import com.codinglitch.someorigins.SomeOrigins;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class RidingPacketS2C {
    public static Identifier ID = new Identifier(SomeOrigins.ID, "start_riding_s2c");

    public static void send(ServerPlayerEntity player, Entity entity) {
        PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
        buffer.writeInt(entity.getId());
        ServerPlayNetworking.send(player, ID, buffer);
    }

    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler network, PacketByteBuf buffer, PacketSender sender) {
        int id = buffer.readInt();
        client.execute(new Runnable() {
           @Override
           public void run() {
               Entity entity = client.world.getEntityById(id);
               if (entity != null) {
                   entity.startRiding(client.player);
               }
           }
       });
    }
}
