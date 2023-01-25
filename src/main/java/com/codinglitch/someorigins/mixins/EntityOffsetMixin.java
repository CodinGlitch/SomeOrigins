package com.codinglitch.someorigins.mixins;

import com.codinglitch.someorigins.registry.powers.GuidancePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin({Entity.class})
public abstract class EntityOffsetMixin {
    @Shadow public abstract @Nullable Entity getFirstPassenger();

    @Shadow public abstract float getHeight();

    @Inject(at = @At("RETURN"), method = "getMountedHeightOffset", cancellable = true)
    private void someorigins$getMountedHeightOffset(CallbackInfoReturnable<Double> info) {

        Entity firstPassenger = this.getFirstPassenger();
        if (firstPassenger != null) {
            if (PowerHolderComponent.hasPower(firstPassenger, GuidancePower.class))
                info.setReturnValue(this.getHeight() * 0.3d + firstPassenger.getHeight() * 0.7d);
        }

    }
}
