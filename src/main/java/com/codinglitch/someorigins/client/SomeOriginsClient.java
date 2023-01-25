package com.codinglitch.someorigins.client;

import com.codinglitch.someorigins.SomeOrigins;
import com.codinglitch.someorigins.packets.RidingPacketS2C;
import io.github.apace100.apoli.ApoliClient;
import io.github.apace100.origins.Origins;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.logging.Logger;

@Environment(EnvType.CLIENT)
public class SomeOriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        initializePackets();

        // Keybinding


        // maybe ill make a seperate class for this
        ArrayList<KeyBinding> keybinds = new ArrayList<>();

        keybinds.add(new KeyBinding("key.origins.ternary_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category." + Origins.MODID));

        for (KeyBinding keybind : keybinds) {
            try { // why no function to check if already bound?
                ApoliClient.registerPowerKeybinding(keybind.getTranslationKey(), keybind);
                KeyBindingHelper.registerKeyBinding(keybind);
            } catch (IllegalArgumentException exception) {
                SomeOrigins.info("A mod already registered the keybind (%s), skipping this one...", keybind.getTranslationKey());
            }
        }
    }

    private void initializePackets() {
        ClientPlayNetworking.registerGlobalReceiver(RidingPacketS2C.ID, RidingPacketS2C::recieve);
    }
}
