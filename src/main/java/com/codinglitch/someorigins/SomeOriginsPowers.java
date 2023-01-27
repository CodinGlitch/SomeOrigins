package com.codinglitch.someorigins;

import com.codinglitch.someorigins.registry.SomeOriginsScaleTypes;
import com.codinglitch.someorigins.registry.powers.*;
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
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;

public class SomeOriginsPowers {
    public static void initialize() {
        Registry.register(ApoliRegistries.POWER_FACTORY, PHOTOPHILLIC.getSerializerId(), PHOTOPHILLIC);
        Registry.register(ApoliRegistries.POWER_FACTORY, GUIDANCE.getSerializerId(), GUIDANCE);
        Registry.register(ApoliRegistries.POWER_FACTORY, AMORPHOUS.getSerializerId(), AMORPHOUS);
        Registry.register(ApoliRegistries.POWER_FACTORY, MARTYRDOM.getSerializerId(), MARTYRDOM);
        Registry.register(ApoliRegistries.POWER_FACTORY, ENERGY_IN_LIGHT.getSerializerId(), ENERGY_IN_LIGHT);
    }


    public static PowerFactory<Power> registerGenericActivePower(String identifier, TriFunction<SerializableData.Instance, PowerType<Power>, LivingEntity, Power> instantiator) {
        return registerPower(identifier, new SerializableData().add("key", ApoliDataTypes.KEY, new Active.Key()), instantiator);
    }
    public static PowerFactory<Power> registerGenericPower(String identifier, TriFunction<SerializableData.Instance, PowerType<Power>, LivingEntity, Power> instantiator) {
        return registerPower(identifier, new SerializableData(), instantiator);
    }
    public static PowerFactory<Power> registerPower(String identifier, SerializableData serializableData, TriFunction<SerializableData.Instance, PowerType<Power>, LivingEntity, Power> instantiator) {
        return new PowerFactory<>(new Identifier(SomeOrigins.ID, identifier), serializableData, data -> (type, entity) -> {
            Power power = instantiator.apply(data, type, entity);
            if (power instanceof Active activePower)
                activePower.setKey(data.get("key"));
            return power;
        }).allowCondition();
    }

    public static final PowerFactory<Power> PHOTOPHILLIC = registerPower("photophillic",
            new SerializableData().add("resource", ApoliDataTypes.POWER_TYPE),
            (data, type, entity) -> new PhotophillicPower(type, entity, data.get("resource"))
    );
    public static final PowerFactory<Power> GUIDANCE = registerGenericActivePower("guidance", (data, type, entity) -> new GuidancePower(type, entity));
    public static final PowerFactory<Power> AMORPHOUS = registerGenericActivePower("amorphous", (data, type, entity) -> new AmorphousPower(type, entity, SomeOriginsScaleTypes.AMORPHOUS_TYPE));
    public static final PowerFactory<Power> MARTYRDOM = registerPower("martyrdom",
            new SerializableData().add("key", ApoliDataTypes.KEY, new Active.Key()).add("resource", ApoliDataTypes.POWER_TYPE),
            (data, type, entity) -> new MartyrdomPower(type, entity, data.get("resource"))
    );

    public static final PowerFactory<Power> ENERGY_IN_LIGHT = registerPower("energy_in_light",
            new SerializableData().add("resource", ApoliDataTypes.POWER_TYPE),
            (data, type, entity) -> new EnergyInLightPower(type, entity, data.get("resource"))
    );
}
