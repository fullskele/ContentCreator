package surreal.contentcreator.common.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.functions.item.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.item.SubItem")
public class SubItem {
    // Functions
    public IItemUnlocalizedNameFunc UNLOCNAME = null;
    public IItemUseFunc ITEMUSE = null;
    public IItemDestroySpeedFunc DESTROYSPEED = null;
    public IItemFloatStackFunc XPREPAIR = null;
    public IItemBlockStartBreakFunc BLOCKSTARTBREAK = null;
    public IItemUsingTickFunc USINGTICK = null;
    public IItemLeftClickEntityFunc LEFTCLICKENTITY = null;
    public IItemContainerItemFunc CONTAINERITEM = null;
    public IItemEntityLifespanFunc ENTITYLIFESPAN = null;
    public IItemCustomEntityFunc CUSTOMENTITY = null;
    public IItemEntityItemUpdateFunc ENTITYUPDATE = null;
    public IItemEntitySwingFunc ENTITYSWING = null;
    public IItemUseFunc ITEMUSEFIRST = null;
    public IItemUseFinishFunc ITEMUSEFINISH = null;
    // damage
    public IItemIntStackFunc ITEMDAMAGE = null;
    public IItemBooleanStackFunc SHOWBAR = null;
    public IItemDoubleStackFunc DURABILITYDISPLAY = null;
    public IItemIntStackFunc COLORDISPLAY = null;
    public IItemIntStackFunc MAXDAMAGE = null;
    public IItemSetDamageFunc SETDAMAGE = null;
    // ------
    public IItemDestroyCreative DESTROYCREATIVE = null;
    public IItemHarvestBlockFunc HARVESTBLOCK = null;
    public IItemIntStackFunc STACKLIMIT = null;
    public IItemReequipAnimation REEQUIP = null;
    public IItemBlockBreakResetFunc BREAKRESET = null;
    public IItemContinueUsingFunc CONTINUEUSING = null;
    public IItemStringStackFunc CREATORMODID = null;
    public IItemIntStackFunc ENCHANTABILITY = null;
    public IItemApplyEnchantmentTableFunc APPLYENCHTABLE = null;
    public IItemBooleanStackFunc BEACONPAYMENT = null;
    public IItemCreatedFunc CREATED = null;
    public IItemIntStackFunc BURNTIME = null;
    public IItemDisableShieldFunc DISABLESHIED = null;
    public IItemStringStackFunc RARITY = null;
    public IItemBooleanStackFunc EFFECT = null;
    public IItemStringStackFunc ACTION = null;
    public IItemIntStackFunc MAXUSEDURATION = null;
    public IItemRightClick RIGHTCLICK = null;
    public IItemHitEntityFunc ENTITYHIT = null;
    public IItemInteractionEntityFunc ENTITYINTERACTION = null;
    public IItemColorFunc COLOR = null;
    public IItemInformation INFO = null;

    // Food
    public IItemIntStackFunc HEALAMOUNT = null;
    public IItemFloatStackFunc SATURATION = null;
    public IItemBooleanStackFunc WOLFSFAVORITE = null;
    public IItemBooleanStackFunc ALWAYSEDIBLE = null;

    // Variables
    public final int meta;
    public String modelLocation; // like location#type

    public Map<ResourceLocation, IItemPropertyFunc> itemProperties = null;
    public Map<String, Integer> toolClasses = null;
    public List<String> oreList = null;

    public SubItem(int meta) {
        this.meta = meta;
    }

    @ZenMethod
    public SubItem addProperty(String name, IItemPropertyFunc func) {
        if (itemProperties == null) itemProperties = new Object2ObjectOpenHashMap<>();
        itemProperties.put(new ResourceLocation(ModValues.MODID, name), func);
        return this;
    }

    @ZenMethod
    public SubItem setUnlocalizedName(IItemUnlocalizedNameFunc func) {
        this.UNLOCNAME = func;
        return this;
    }

    @ZenMethod
    public SubItem setUnlocalizedName(String unlocName) {
        this.UNLOCNAME = stack -> unlocName;
        return this;
    }

    @ZenMethod
    public SubItem setItemUse(IItemUseFunc func) {
        this.ITEMUSE = func;
        return this;
    }

    @ZenMethod
    public SubItem setDestroySpeed(IItemDestroySpeedFunc func) {
        this.DESTROYSPEED = func;
        return this;
    }

    @ZenMethod
    public SubItem setDestroySpeed(float value) {
        this.DESTROYSPEED = (stack, state) -> value;
        return this;
    }

    @ZenMethod
    public SubItem setXpRepair(IItemFloatStackFunc func) {
        this.XPREPAIR = func;
        return this;
    }

    @ZenMethod
    public SubItem setXpRepair(float ratio) {
        this.XPREPAIR = stack -> ratio;
        return this;
    }

    @ZenMethod
    public SubItem setBlockStartBreak(IItemBlockStartBreakFunc func) {
        this.BLOCKSTARTBREAK = func;
        return this;
    }

    @ZenMethod
    public SubItem setUsingTick(IItemUsingTickFunc func) {
        this.USINGTICK = func;
        return this;
    }

    @ZenMethod
    public SubItem setLeftClickEntity(IItemLeftClickEntityFunc func) {
        this.LEFTCLICKENTITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setContainerItem(IItemContainerItemFunc func) {
        this.CONTAINERITEM = func;
        return this;
    }

    @ZenMethod
    public SubItem setContainerItem(IItemStack stack) {
        this.CONTAINERITEM = stack1 -> stack;
        return this;
    }

    @ZenMethod
    public SubItem setEntityLifespan(IItemEntityLifespanFunc func) {
        this.ENTITYLIFESPAN = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntityLifespan(int time) {
        this.ENTITYLIFESPAN = (stack, world) -> time;
        return this;
    }

    @ZenMethod
    public SubItem setCustomEntity(IItemCustomEntityFunc func) {
        this.CUSTOMENTITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setCustomEntity(IEntityDefinition definition) {
        this.CUSTOMENTITY = (world, entity, stack) -> {
            IEntity en = definition.createEntity(world);
            en.setPosition(entity.getPosition());
            return en;
        };
        return this;
    }

    @ZenMethod
    public SubItem setEntityItemUpdate(IItemEntityItemUpdateFunc func) {
        this.ENTITYUPDATE = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntitySwing(IItemEntitySwingFunc func) {
        this.ENTITYSWING = func;
        return this;
    }

    @ZenMethod
    public SubItem setItemDamageGetter(IItemIntStackFunc func) {
        this.ITEMDAMAGE = func;
        return this;
    }

    @ZenMethod
    public SubItem shouldShowDurabilityBar(IItemBooleanStackFunc func) {
        this.SHOWBAR = func;
        return this;
    }

    @ZenMethod
    public SubItem setDurabilityPercent(IItemDoubleStackFunc func) {
        this.DURABILITYDISPLAY = func;
        return this;
    }

    @ZenMethod
    public SubItem setDurabilityColor(IItemIntStackFunc func) {
       this.COLORDISPLAY = func;
       return this;
    }

    @ZenMethod
    public SubItem setMaxDamageGetter(IItemIntStackFunc func) {
        this.MAXDAMAGE = func;
        return this;
    }

    @ZenMethod
    public SubItem setDamageSetter(IItemSetDamageFunc func) {
        this.SETDAMAGE = func;
        return this;
    }

    @ZenMethod
    public SubItem setDamage(int maxDamage) {
        this.CREATED = (stack, world, player) -> {
            ItemStack st = (ItemStack) stack.getInternal();
            if (!st.hasTagCompound()) st.setTagCompound(new NBTTagCompound());
            st.getTagCompound().setInteger("damage", 0);
        };
        this.MAXDAMAGE = stack -> maxDamage;
        this.ITEMDAMAGE = stack -> ((ItemStack) stack.getInternal()).getTagCompound().getInteger("damage");
        this.SETDAMAGE = (stack, damageToSet) -> ((ItemStack) stack.getInternal()).getTagCompound().setInteger("damage", damageToSet);

        return this;
    }

    @ZenMethod
    public SubItem canDestroyInCreative(IItemDestroyCreative func) {
        this.DESTROYCREATIVE = func;
        return this;
    }

    @ZenMethod
    public SubItem canHarvestBlock(IItemHarvestBlockFunc func) {
        this.HARVESTBLOCK = func;
        return this;
    }

    @ZenMethod
    public SubItem setMaxStackSize(IItemIntStackFunc func) {
        this.STACKLIMIT = func;
        return this;
    }

    @ZenMethod
    public SubItem setMaxStackSize(int value) {
        this.STACKLIMIT = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem addToolClass(String tool, int level) {
        if (this.toolClasses == null) this.toolClasses = new Object2ObjectOpenHashMap<>();
        this.toolClasses.put(tool, level);
        return this;
    }

    @ZenMethod
    public SubItem causeReequipAnimation(IItemReequipAnimation func) {
        this.REEQUIP = func;
        return this;
    }

    @ZenMethod
    public SubItem causeBlockBreakReset(IItemBlockBreakResetFunc func) {
        this.BREAKRESET = func;
        return this;
    }

    @ZenMethod
    public SubItem setContinueUsing(IItemContinueUsingFunc func) {
        this.CONTINUEUSING = func;
        return this;
    }

    @ZenMethod
    public SubItem setContinueUsing() {
        this.CONTINUEUSING = (oldStack, newStack) -> true;
        return this;
    }

    @ZenMethod
    public SubItem setCreatorModId(IItemStringStackFunc func) {
        this.CREATORMODID = func;
        return this;
    }

    @ZenMethod
    public SubItem setEnchantability(IItemIntStackFunc func) {
        this.ENCHANTABILITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setEnchantability(int value) {
        this.ENCHANTABILITY = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem canApplyEnchantment(IItemApplyEnchantmentTableFunc func) {
        this.APPLYENCHTABLE = func;
        return this;
    }

    @ZenMethod
    public SubItem setBeaconPayment(IItemBooleanStackFunc func) {
        this.BEACONPAYMENT = func;
        return this;
    }

    @ZenMethod
    public SubItem setBeaconPayment() {
        this.BEACONPAYMENT = stack -> true;
        return this;
    }

    @ZenMethod
    public SubItem setItemUseFirst(IItemUseFunc func) {
        this.ITEMUSEFIRST = func;
        return this;
    }

    @ZenMethod
    public SubItem setItemCreated(IItemCreatedFunc func) {
        this.CREATED = func;
        return this;
    }

    @ZenMethod
    public SubItem setBurnTime(IItemIntStackFunc func) {
        this.BURNTIME = func;
        return this;
    }

    @ZenMethod
    public SubItem setBurnTime(int value) {
        this.BURNTIME = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem canDisableShield(IItemDisableShieldFunc func) {
        this.DISABLESHIED = func;
        return this;
    }

    @ZenMethod
    public SubItem setTooltip(IItemInformation func) {
        this.INFO = func;
        return this;
    }

    @ZenMethod
    public SubItem setTooltip(String[] string) {
        this.INFO = (stack, world, isAdvanced) -> string;
        return this;
    }

    @ZenMethod
    public SubItem setOres(String... ores) {
        if (this.oreList == null) this.oreList = new ArrayList<>();
        Collections.addAll(this.oreList, ores);
        return this;
    }

    @ZenMethod
    public SubItem setRarity(IItemStringStackFunc func) {
        this.RARITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setRarity(String name) {
        this.RARITY = stack -> name;
        return this;
    }

    @ZenMethod
    public SubItem setEffect(IItemBooleanStackFunc func) {
        this.EFFECT = func;
        return this;
    }

    @ZenMethod
    public SubItem setEffect(boolean value) {
        this.EFFECT = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem setUseAction(IItemStringStackFunc func) {
        this.ACTION = func;
        return this;
    }

    @ZenMethod
    public SubItem setUseAction(String value) {
        this.ACTION = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem setMaxUseDuration(IItemIntStackFunc func) {
        this.MAXUSEDURATION = func;
        return this;
    }

    @ZenMethod
    public SubItem setMaxUseDuration(int value) {
        this.MAXUSEDURATION = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem setRightClick(IItemRightClick func) {
        this.RIGHTCLICK = func;
        return this;
    }

    @ZenMethod
    public SubItem setModelLocation(String model) {
        StringBuilder builder = new StringBuilder(ModValues.MODID);
        builder.append(':');
        if (model.contains(":")) model = model.replace(":", ".");
        builder.append(model.trim().toLowerCase());
        if (!model.contains("#")) builder.append("#inventory");
        this.modelLocation = builder.toString();
        return this;
    }

    // Food Stuff
    @ZenMethod
    public SubItem setHealAmount(IItemIntStackFunc func) {
        this.HEALAMOUNT = func;
        return this;
    }

    @ZenMethod
    public SubItem setHealAmount(int amount) {
        this.HEALAMOUNT = stack -> amount;
        return this;
    }

    @ZenMethod
    public SubItem setSaturation(IItemFloatStackFunc func) {
        this.SATURATION = func;
        return this;
    }

    @ZenMethod
    public SubItem setSaturation(float amount) {
        this.SATURATION = stack -> amount;
        return this;
    }

    @ZenMethod
    public SubItem setWolfsFavorite(IItemBooleanStackFunc func) {
        this.WOLFSFAVORITE = func;
        return this;
    }

    @ZenMethod
    public SubItem setWolfsFavorite() {
        this.WOLFSFAVORITE = stack -> true;
        return this;
    }

    @ZenMethod
    public SubItem setAlwaysEdible(IItemBooleanStackFunc func) {
        this.ALWAYSEDIBLE = func;
        return this;
    }

    @ZenMethod
    public SubItem setAlwaysEdible() {
        this.ALWAYSEDIBLE = stack -> true;
        return this;
    }

    @ZenMethod
    public SubItem setUseFinish(IItemUseFinishFunc func) {
        this.ITEMUSEFINISH = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntityHit(IItemHitEntityFunc func) {
        this.ENTITYHIT = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntityInteract(IItemInteractionEntityFunc func) {
        this.ENTITYINTERACTION = func;
        return this;
    }

    @ZenClass
    public SubItem setColor(IItemColorFunc func) {
        this.COLOR = func;
        return this;
    }
}