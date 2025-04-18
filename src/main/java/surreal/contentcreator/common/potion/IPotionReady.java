package surreal.contentcreator.common.potion;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("contentcreator.potion.IPotionReady")
public interface IPotionReady {
    @ZenMethod
    boolean isReady(int duration, int amplifier);
}
