package surreal.contentcreator.common.potion;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.CommonProxy;

@ZenRegister
@ZenClass("contentcreator.potion.Potion")
public class PotionBase extends Potion {
    private boolean isBeneficial = true;
    private boolean isInstant = false;
    private boolean shouldRender = true;
    private boolean shouldRenderHUD = true;
    private boolean shouldRenderInvText = true;
    private IPotionEffectApplier effectApplier = null;
    private IPotionReady potionReady = null;
    private ResourceLocation icon = null;

    protected PotionBase(boolean isBadEffect, int color) {
        super(isBadEffect, color);
    }

    @ZenMethod
    public static PotionBase create(String name, boolean isNegative, int color) {
        PotionBase potion = new PotionBase(isNegative, color);
        potion.setPotionName("effect." + name);
        potion.setRegistryName(new ResourceLocation("contentcreator", name));
        return potion;
    }

    @ZenMethod
    public void register() {
        CommonProxy.POTIONS.add(this);
    }

    @ZenMethod
    public PotionBase setInstant(boolean instant) {
        this.isInstant = instant;
        return this;
    }

    @ZenMethod
    public PotionBase setBeneficial(boolean beneficial) {
        this.isBeneficial = beneficial;
        return this;
    }

    @ZenMethod
    public PotionBase setShouldRender(boolean render) {
        this.shouldRender = render;
        return this;
    }

    @ZenMethod
    public PotionBase setShouldRenderHUD(boolean renderHUD) {
        this.shouldRenderHUD = renderHUD;
        return this;
    }

    @ZenMethod
    public PotionBase setShouldRenderInvText(boolean renderInvText) {
        this.shouldRenderInvText = renderInvText;
        return this;
    }

    @ZenMethod
    public PotionBase setEffectApplier(IPotionEffectApplier applier) {
        this.effectApplier = applier;
        return this;
    }

    @ZenMethod
    public PotionBase setPotionReady(IPotionReady ready) {
        this.potionReady = ready;
        return this;
    }

    @Override
    public boolean isInstant() {
        return isInstant;
    }

    @Override
    public boolean isBeneficial() {
        return isBeneficial;
    }

    @Override
    public boolean shouldRender(PotionEffect effect) {
        return this.shouldRender;
    }

    @Override
    public boolean shouldRenderHUD(PotionEffect effect) {
        return this.shouldRenderHUD;
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect) {
        return this.shouldRenderInvText;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (effectApplier != null) {
            effectApplier.applyEffect(CraftTweakerMC.getIEntityLivingBase(entity), amplifier);
        }
        super.performEffect(entity, amplifier);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (potionReady != null) {
            return potionReady.isReady(duration, amplifier);
        }
        return super.isReady(duration, amplifier);
    }

    @ZenMethod
    public void setIcon(String name) {
        this.setIcon(new ResourceLocation(ModValues.MODID, "textures/potions/" + name + ".png"));
    }

    public ResourceLocation getIcon(PotionEffect effect) {
        return icon;
    }

    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        if (icon == null) return;
        mc.renderEngine.bindTexture(getIcon(effect));
        Gui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 16, 16, 16, 16);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z) {
        renderEffect(effect, x + 6, y + 7, 1);
    }

    @SideOnly(Side.CLIENT)
    protected void renderEffect(PotionEffect effect, int x, int y, float alpha) {
        if (icon == null) return;
        GlStateManager.pushMatrix();
        GlStateManager.color(1, 1, 1, alpha);
        Minecraft.getMinecraft().renderEngine.bindTexture(getIcon(effect));
        Gui.drawScaledCustomSizeModalRect(x, y, 0, 0 , 18, 18, 18, 18, 18, 18);
        GlStateManager.popMatrix();
    }
}