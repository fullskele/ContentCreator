package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import surreal.contentcreator.types.CTSoundType;

import javax.annotation.Nullable;
import java.util.Random;

/*
    Much code was taken from Better With Mods - Hemp blocks.
    Credit to BeetoGuy, primetoxinz, Koward, BordListan, MRebhan
*/

public class BlockGenericCropTall extends BlockCrops implements IGenericBlock, IPlantable {

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

    public static final PropertyBool TOP = PropertyBool.create("top");
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[]{
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 0.125D, 13 / 16d),
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 0.25D, 13 / 16d),
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 0.375D, 13 / 16d),
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 0.5d, 13 / 16d),
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 0.625D, 13 / 16d),
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 0.75D, 13 / 16d),
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 0.875D, 13 / 16d),
            new AxisAlignedBB(3 / 16d, 0.0D, 3 / 16d, 13 / 16d, 1.0D, 13 / 16d)};

    public BlockGenericCropTall(String c, int meta, int cMin, int cMax) {
        super();

        cropItemExists = true;
        cropID = c;
        cropMeta = meta;

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cropID));
        ItemStack itemStack = item == null ? null : new ItemStack(item, 1, cropMeta);
        crop = itemStack == null ? null : itemStack;

        this.cMin = Math.max(cMin, 1);
        this.cMax = Math.max(cMin, cMax);

        this.setDefaultState(getDefaultState().withProperty(TOP, false));
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CROPS_AABB[state.getValue(this.getAgeProperty())];
    }

    @Override
    public boolean isMaxAge(IBlockState state) {
        return state.getValue(AGE) > 6;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        IBlockState above = worldIn.getBlockState(pos.up());
        //TODO simplify?
        boolean isTop = state.getValue(TOP);
        if (isTop) {
            return !isMaxAge(state);
        }
        return !(above.getBlock() instanceof BlockGenericCropTall);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        if (state.getValue(TOP)) {
            IBlockState belowState = world.getBlockState(pos.down());
            return belowState.getBlock() instanceof BlockGenericCropTall && !belowState.getValue(TOP);
        } else {
            return (world.getLight(pos) > 12 || world.canSeeSky(pos) || world.canSeeSky(pos.up()))
                    && (canBePlantedHere(world, pos) || canPlantGrowOnBlock(world.getBlockState(pos.down())));
        }
    }

    public boolean canBePlantedHere(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock().canSustainPlant(world.getBlockState(pos.down()), world, pos.down(), EnumFacing.UP, this);
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        if (!state.getValue(TOP) && state.getValue(AGE) >= 7) {
            worldIn.setBlockState(pos.up(), state.withProperty(AGE, 0).withProperty(TOP, true));
        }
        boolean isTop = state.getValue(TOP);

        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (i > j) {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i).withProperty(TOP, isTop), 2);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        checkAndDropBlock(world, pos, state);
        BlockPos up = pos.up();

        int age = state.getValue(AGE);
        boolean isTop = state.getValue(TOP);

        float growthChance = getGrowthChance(this, world, pos);

        if (age < 7) {
            if (world.getLightFromNeighbors(up) > 12) {
                if (rand.nextInt(MathHelper.ceil(growthChance)) == 0)
                    world.setBlockState(pos, state.withProperty(AGE, age + 1).withProperty(TOP, isTop));
            }
        } else if (age == 7 && world.isAirBlock(up) && !isTop) {
            if (world.getLightFromNeighbors(up) > 12) {
                if (rand.nextInt(MathHelper.ceil(growthChance * 2)) == 0)
                    world.setBlockState(up, state.withProperty(AGE, 0).withProperty(TOP, true));
            }
        }
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(this, 1, 0);
    }

    public boolean canPlantGrowOnBlock(IBlockState state) {
        Block block = state.getBlock();
        return block == this && state.getValue(AGE) == 7;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos);
    }

    @Override
    protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
        if (!canBlockStay(world, pos, state)) {
            dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);

            if (!state.getValue(TOP)) {
                IBlockState aboveState = world.getBlockState(pos.up());
                if (aboveState.getBlock() instanceof BlockGenericCropTall && aboveState.getValue(TOP)) {
                    world.setBlockToAir(pos.up());
                }
            }
        }
    }


    @Override
    public int damageDropped(IBlockState state) {
        int meta = state.getValue(AGE);
        return meta > 6 ? 2 : 0;
    }

    @Override
    protected Item getSeed() {
        return seed.getItem();
    }

    @Override
    protected Item getCrop() {
        return tryGetCrop().getItem();
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, TOP);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta == 8)
            return super.getStateFromMeta(7).withProperty(TOP, true);
        return super.getStateFromMeta(meta).withProperty(TOP, false);
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
    public int getMetaFromState(IBlockState state) {
        if (state.getValue(TOP))
            return 8;
        return super.getMetaFromState(state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        int meta = state.getValue(AGE);
        return meta > 6 ? this.getCrop() : this.getSeed();
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return getAge(state) >= getMaxAge() ? (random.nextInt((cMax - cMin) + 1) + cMin) * Math.max(1, fortune) : 0;
    }

    @Override
    public Item createItem(Block block) {
        Item seeds = new ItemSeeds(block, Blocks.FARMLAND).setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName());
        this.seed = new ItemStack(seeds);
        return seeds;
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
