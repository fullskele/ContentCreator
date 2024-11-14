package surreal.contentcreator.functions.enchantment;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.enchantment.functions.IOnUserHurt")
public interface IEnchantmentOnUserHurtAction {
    void onUserHurt(IEntityLivingBase user, IEntity attacker, int level);
}
