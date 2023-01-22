package com.codinglitch.someorigins.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin({Entity.class})
public abstract class EntityOffsetMixin {
    @Inject(at = @At("RETURN"), method = "getMountedHeightOffset", cancellable = true)
    private void someorigins$getMountedHeightOffset(CallbackInfoReturnable<Double> info) {
        Entity self = ((Entity)(Object)this);
        Entity firstPassenger = self.getFirstPassenger();
        if (firstPassenger != null)
            info.setReturnValue(self.getHeight() * 0.3d + firstPassenger.getHeight() * 0.7d);//self.getHeight() - firstPassenger.getHeight() * 0.5);
    }

    @Inject(at = @At("RETURN"), method = "getHeightOffset", cancellable = true)
    private void someorigins$getHeightOffset(CallbackInfoReturnable<Double> info) {
        Entity self = ((Entity)(Object)this);
        info.setReturnValue(0d);
    }
}
