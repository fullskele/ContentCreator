package surreal.contentcreator.common.enchantment.wrapper;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.enchantment.Enchantment;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("contentcreator.enchantment.Rarity")
public class RarityWrapper {
    public final Enchantment.Rarity rarity;
    public RarityWrapper(Enchantment.Rarity rarity) {
        this.rarity = rarity;
    }

    @ZenProperty
    public static final RarityWrapper common = new RarityWrapper(Enchantment.Rarity.COMMON);
    @ZenProperty
    public static final RarityWrapper uncommon = new RarityWrapper(Enchantment.Rarity.UNCOMMON);
    @ZenProperty
    public static final RarityWrapper rare = new RarityWrapper(Enchantment.Rarity.RARE);
    @ZenProperty
    public static final RarityWrapper veryRare = new RarityWrapper(Enchantment.Rarity.VERY_RARE);
}
