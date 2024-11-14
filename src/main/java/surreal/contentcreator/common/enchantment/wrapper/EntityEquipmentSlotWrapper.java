package surreal.contentcreator.common.enchantment.wrapper;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.inventory.EntityEquipmentSlot;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("contentcreator.enchantment.EquipmentSlot")
public class EntityEquipmentSlotWrapper {
    public final EntityEquipmentSlot slot;
    public EntityEquipmentSlotWrapper(EntityEquipmentSlot slot) {
        this.slot = slot;
    }

    @ZenProperty
    public static final EntityEquipmentSlotWrapper mainHand = new EntityEquipmentSlotWrapper(EntityEquipmentSlot.MAINHAND);
    @ZenProperty
    public static final EntityEquipmentSlotWrapper offHand = new EntityEquipmentSlotWrapper(EntityEquipmentSlot.OFFHAND);
    @ZenProperty
    public static final EntityEquipmentSlotWrapper feet = new EntityEquipmentSlotWrapper(EntityEquipmentSlot.FEET);
    @ZenProperty
    public static final EntityEquipmentSlotWrapper legs = new EntityEquipmentSlotWrapper(EntityEquipmentSlot.LEGS);
    @ZenProperty
    public static final EntityEquipmentSlotWrapper chest = new EntityEquipmentSlotWrapper(EntityEquipmentSlot.CHEST);
    @ZenProperty
    public static final EntityEquipmentSlotWrapper head = new EntityEquipmentSlotWrapper(EntityEquipmentSlot.HEAD);
}
