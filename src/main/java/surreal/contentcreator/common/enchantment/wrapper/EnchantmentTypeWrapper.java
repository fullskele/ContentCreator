package surreal.contentcreator.common.enchantment.wrapper;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.enchantment.EnumEnchantmentType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("contentcreator.enchantment.EnchantmentType")
public class EnchantmentTypeWrapper {
    public final EnumEnchantmentType type;
    public EnchantmentTypeWrapper(EnumEnchantmentType type) {
        this.type = type;
    }

    @ZenProperty
    public static final EnchantmentTypeWrapper all = new EnchantmentTypeWrapper(EnumEnchantmentType.ALL);
    @ZenProperty
    public static final EnchantmentTypeWrapper armor = new EnchantmentTypeWrapper(EnumEnchantmentType.ARMOR);
    @ZenProperty
    public static final EnchantmentTypeWrapper armorFeet = new EnchantmentTypeWrapper(EnumEnchantmentType.ARMOR_FEET);
    @ZenProperty
    public static final EnchantmentTypeWrapper armorLegs = new EnchantmentTypeWrapper(EnumEnchantmentType.ARMOR_LEGS);
    @ZenProperty
    public static final EnchantmentTypeWrapper armorChest = new EnchantmentTypeWrapper(EnumEnchantmentType.ARMOR_CHEST);
    @ZenProperty
    public static final EnchantmentTypeWrapper armorHead = new EnchantmentTypeWrapper(EnumEnchantmentType.ARMOR_HEAD);
    @ZenProperty
    public static final EnchantmentTypeWrapper weapon = new EnchantmentTypeWrapper(EnumEnchantmentType.WEAPON);
    @ZenProperty
    public static final EnchantmentTypeWrapper digger = new EnchantmentTypeWrapper(EnumEnchantmentType.DIGGER);
    @ZenProperty
    public static final EnchantmentTypeWrapper fishingRod = new EnchantmentTypeWrapper(EnumEnchantmentType.FISHING_ROD);
    @ZenProperty
    public static final EnchantmentTypeWrapper breakable = new EnchantmentTypeWrapper(EnumEnchantmentType.BREAKABLE);
    @ZenProperty
    public static final EnchantmentTypeWrapper bow = new EnchantmentTypeWrapper(EnumEnchantmentType.BOW);
    @ZenProperty
    public static final EnchantmentTypeWrapper wearable = new EnchantmentTypeWrapper(EnumEnchantmentType.WEARABLE);
}
