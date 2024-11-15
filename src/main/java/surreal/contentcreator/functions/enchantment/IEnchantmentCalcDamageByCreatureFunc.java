package surreal.contentcreator.functions.enchantment;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.enchantment.functions.ICalcDamageByCreature")
public interface IEnchantmentCalcDamageByCreatureFunc {
    float calcDamageByCreature(int level, String creatureType);
}
