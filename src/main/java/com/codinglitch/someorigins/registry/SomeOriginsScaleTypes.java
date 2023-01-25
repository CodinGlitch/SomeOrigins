package com.codinglitch.someorigins.registry;

import com.codinglitch.someorigins.SomeOrigins;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.*;

import java.util.Map;

public class SomeOriginsScaleTypes {
    private static final ScaleType[] AMORPHOUS_SIZE_TYPES = {ScaleTypes.WIDTH, ScaleTypes.HEIGHT, ScaleTypes.DROPS, ScaleTypes.VISIBILITY};
    public static final ScaleModifier AMORPHOUS_MODIFIER = register(ScaleRegistries.SCALE_MODIFIERS, new TypedScaleModifier(() -> SomeOriginsScaleTypes.AMORPHOUS_TYPE));
    public static final ScaleType AMORPHOUS_TYPE = register(ScaleRegistries.SCALE_TYPES, ScaleType.Builder.create().addDependentModifier(AMORPHOUS_MODIFIER).affectsDimensions().build());

    private static <T> T register(Map<Identifier, T> registry, T entry) {
        return ScaleRegistries.register(registry, new Identifier(SomeOrigins.ID, "modify_size"), entry);
    }

    public static void initialize() {
        for (ScaleType type : AMORPHOUS_SIZE_TYPES) {
            type.getDefaultBaseValueModifiers().add(AMORPHOUS_MODIFIER);
        }
    }
}
