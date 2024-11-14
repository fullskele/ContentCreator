package surreal.contentcreator.functions.enchantment;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.enchantment.functions.IOnEntityDamaged")
public interface IEnchantmentOnEntityDamagedAction {
    void onEntityDamaged(IEntityLivingBase user, IEntity target, int level);
}
