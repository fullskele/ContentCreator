package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import surreal.contentcreator.types.CTSoundType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class BlockGenericCrop extends BlockCrops implements IGenericBlock {
    private boolean cropItemExists;
    private String cropID;
    private int cropMeta;
    private ItemStack seed, crop;
    private int cMin, cMax;

    @Nullable
    private ItemStack tryGetCrop() {
        if (crop == null && cropItemExists) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cropID));
            if (item != null) crop = new ItemStack(item, 1, cropMeta);
            if (crop == null) cropItemExists = false;
        }
        return crop;
    }

    public BlockGenericCrop(String c, int meta, int cMin, int cMax) {
        cropItemExists = true;
        cropID = c;
        cropMeta = meta;

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cropID));
        ItemStack itemStack = item == null ? null : new ItemStack(item, 1, cropMeta);
        crop = itemStack == null ? null : itemStack;

        this.cMin = Math.max(cMin, 1);
        this.cMax = Math.max(cMin, cMax);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        int count = quantityDropped(state, fortune, rand);

        ItemStack itemStack = tryGetCrop();
        if (itemStack != null)
            for (int i = 0; i < count; i++)
                drops.add(itemStack.copy());

        drops.add(seed.copy());
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            List<ItemStack> drops = getDrops(worldIn, pos, state, fortune); // use the old method until it gets removed, for backward compatibility
            chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());

            for (ItemStack drop : drops) {
                if (worldIn.rand.nextFloat() <= chance)
                    spawnAsEntity(worldIn, pos, drop);
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.isMaxAge(state) ? tryGetCrop().getMetadata() : seed.getMetadata();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isMaxAge(state) ? tryGetCrop().getItem() : seed.getItem();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return seed;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return getAge(state) >= getMaxAge() ? (random.nextInt((cMax - cMin) + 1) + cMin) * Math.max(1, fortune) : 0;
    }

    @Override
    protected Item getCrop() {
        return tryGetCrop().getItem();
    }

    @Override
    protected Item getSeed() {
        return seed.getItem();
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }

    @Override
    public Item createItem(Block block) {
        Item seeds = new ItemSeeds(block, Blocks.FARMLAND).setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName());
        this.seed = new ItemStack(seeds);
        return seeds;
    }
}
