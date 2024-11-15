package surreal.contentcreator.functions.enchantment;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.enchantment.functions.ICalcModifierDamage")
public interface IEnchantmentCalcModifierDamageFunc {
    int calcModifierDamage(int level, IDamageSource source);
}
