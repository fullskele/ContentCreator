package surreal.contentcreator.common.enchantment;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.common.enchantment.wrapper.EnchantmentTypeWrapper;
import surreal.contentcreator.common.enchantment.wrapper.EntityEquipmentSlotWrapper;
import surreal.contentcreator.common.enchantment.wrapper.RarityWrapper;
import surreal.contentcreator.functions.enchantment.*;
import surreal.contentcreator.proxy.CommonProxy;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@ZenRegister
@ZenClass("contentcreator.enchantment.Enchantment")
public class EnchantmentBase extends Enchantment {
    protected EnchantmentBase(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    private boolean allowedOnBooks = true;
    private boolean treasure = false;
    private boolean curse = false;
    private List<String> incompatibleEnchs = new ArrayList<>();
    private int minLevel = 1;
    private int maxLevel = 1;
    private IEnchantmentMinEnchantabilityFunc MIN_ENCHANTABILITY = null;
    private IEnchantmentMaxEnchantabilityFunc MAX_ENCHANTABILITY = null;
    private IEnchantmentCalcModifierDamageFunc CALC_MODIFIER_DAMAGE = null;
    private IEnchantmentCalcDamageByCreatureFunc CALC_DAMAGE_BY_CREATURE = null;
    private IEnchantmentOnEntityDamagedAction ON_ENTITY_DAMAGED = null;
    private IEnchantmentOnUserHurtAction ON_USER_HURT = null;

    @ZenMethod
    public static EnchantmentBase create(String name, RarityWrapper rarityWrapper, EnchantmentTypeWrapper enchantmentTypeWrapper, EntityEquipmentSlotWrapper[] slotWrappers) {
        EntityEquipmentSlot[] slots = new EntityEquipmentSlot[slotWrappers.length];
        for (int i = 0; i < slots.length; i++)
            slots[i] = slotWrappers[i].slot;
        EnchantmentBase enchantmentBase = new EnchantmentBase(rarityWrapper.rarity, enchantmentTypeWrapper.type, slots);
        enchantmentBase.setName(name);
        enchantmentBase.setRegistryName("contentcreator:" + name);
        return enchantmentBase;
    }

    @ZenMethod
    public void register() {
        CommonProxy.ENCHANTMENTS.add(this);
    }

    @ZenMethod
    public EnchantmentBase setAllowedOnBooks(boolean allowedOnBooks) {
        this.allowedOnBooks = allowedOnBooks;
        return this;
    }

    @ZenMethod
    public EnchantmentBase setTreasure(boolean treasure) {
        this.treasure = treasure;
        return this;
    }

    @ZenMethod
    public EnchantmentBase setCurse(boolean curse) {
        this.curse = curse;
        return this;
    }

    @ZenMethod
    public EnchantmentBase addIncompatible(String name) {
        incompatibleEnchs.add(name);
        return this;
    }

    @ZenMethod
    public EnchantmentBase setMinLevel(int minLevel) {
        this.minLevel = Math.max(1, minLevel);
        return this;
    }

    @ZenMethod
    public EnchantmentBase setMaxLevel(int maxLevel) {
        this.maxLevel = Math.max(minLevel, maxLevel);
        return this;
    }

    @ZenMethod
    public EnchantmentBase setMinEnchantability(IEnchantmentMinEnchantabilityFunc func) {
        MIN_ENCHANTABILITY = func;
        return this;
    }

    @ZenMethod
    public EnchantmentBase setMaxEnchantability(IEnchantmentMaxEnchantabilityFunc func) {
        MAX_ENCHANTABILITY = func;
        return this;
    }

    @ZenMethod
    public EnchantmentBase setCalcModifierDamage(IEnchantmentCalcModifierDamageFunc func) {
        CALC_MODIFIER_DAMAGE = func;
        return this;
    }

    @ZenMethod
    public EnchantmentBase setOnEntityDamaged(IEnchantmentOnEntityDamagedAction func) {
        ON_ENTITY_DAMAGED = func;
        return this;
    }

    @ZenMethod
    public EnchantmentBase setOnUserHurt(IEnchantmentOnUserHurtAction func) {
        ON_USER_HURT = func;
        return this;
    }



    @Override
    public int getMinLevel() {
        return minLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        if (MIN_ENCHANTABILITY == null)
            return super.getMinEnchantability(enchantmentLevel);
        else
            return MIN_ENCHANTABILITY.getMinEnchantability(enchantmentLevel);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        if (MAX_ENCHANTABILITY == null)
            return super.getMaxEnchantability(enchantmentLevel);
        else
            return MAX_ENCHANTABILITY.getMaxEnchantability(enchantmentLevel);
    }

    @Override
    public int calcModifierDamage(int level, DamageSource source) {
        if (CALC_MODIFIER_DAMAGE == null)
            return super.calcModifierDamage(level, source);
        else
            return CALC_MODIFIER_DAMAGE.calcModifierDamage(level, CraftTweakerMC.getIDamageSource(source));
    }

    @Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
        if (CALC_DAMAGE_BY_CREATURE == null)
            return super.calcDamageByCreature(level, creatureType);
        else
            return CALC_DAMAGE_BY_CREATURE.calcDamageByCreature(level, creatureType.toString().toLowerCase());
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        boolean flag = true;
        String registryName = ench.getRegistryName().toString();
        if (this.getRegistryName().toString().equals(registryName)) flag = false;
        for (String incompatibleRegistryName: incompatibleEnchs)
            if (incompatibleRegistryName.equals(registryName))
                flag = false;
        return flag;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return treasure;
    }

    @Override
    public boolean isCurse() {
        return curse;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
        if (ON_ENTITY_DAMAGED == null)
            super.onEntityDamaged(user, target, level);
        else
            ON_ENTITY_DAMAGED.onEntityDamaged(CraftTweakerMC.getIEntityLivingBase(user), CraftTweakerMC.getIEntity(target), level);
    }

    @Override
    public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
        if (ON_USER_HURT == null)
            super.onEntityDamaged(user, attacker, level);
        else
            ON_USER_HURT.onUserHurt(CraftTweakerMC.getIEntityLivingBase(user), CraftTweakerMC.getIEntity(attacker), level);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return allowedOnBooks;
    }
}
