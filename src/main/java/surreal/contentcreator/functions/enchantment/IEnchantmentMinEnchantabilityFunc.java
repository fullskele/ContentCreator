package surreal.contentcreator.functions.enchantment;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.enchantment.functions.IMinEnchantability")
public interface IEnchantmentMinEnchantabilityFunc {
    int getMinEnchantability(int enchantmentLevel);
}
