package surreal.contentcreator.functions.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.item.functions.IApplyEnchantment")
public interface IItemApplyEnchantmentTableFunc {
    boolean canApplyAtEnchantingTable(IItemStack stack, IEnchantmentDefinition enchantmentDef);
}
