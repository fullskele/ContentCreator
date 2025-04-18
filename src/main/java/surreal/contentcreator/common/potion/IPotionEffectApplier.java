package surreal.contentcreator.common.potion;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("contentcreator.potion.IPotionEffectApplier")
public interface IPotionEffectApplier {
    @ZenMethod
    void applyEffect(IEntityLivingBase entity, int amplifier);
}