package surreal.contentcreator.functions.enchantment;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.enchantment.functions.IMaxEnchantability")
public interface IEnchantmentMaxEnchantabilityFunc {
    int getMaxEnchantability(int enchantmentLevel);
}
