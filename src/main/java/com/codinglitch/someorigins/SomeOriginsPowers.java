package com.codinglitch.someorigins;

import com.codinglitch.someorigins.registry.powers.GuidancePower;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiFunction;

public class SomeOriginsPowers {
    public static void initialize() {
        Registry.register(ApoliRegistries.POWER_FACTORY, GUIDANCE.getSerializerId(), GUIDANCE);
    }

    public static PowerFactory<Power> registerPower(String identifier, BiFunction<PowerType<Power>, LivingEntity, Power> instantiator) {
        PowerFactory<Power> factory = new PowerFactory<>(new Identifier(SomeOrigins.ID, identifier), new SerializableData().add("key", ApoliDataTypes.KEY, new Active.Key()), data -> (type, entity) -> {
            Power power = instantiator.apply(type, entity);
            if (power instanceof Active activePower)
                activePower.setKey(data.get("key"));
            return power;
        }).allowCondition();



        return factory;
    }

    public static final PowerFactory<Power> GUIDANCE = registerPower("guidance", GuidancePower::new);
}
