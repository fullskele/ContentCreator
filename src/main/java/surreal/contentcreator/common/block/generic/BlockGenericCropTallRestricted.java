package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Random;

public class BlockGenericCropTallRestricted extends BlockGenericCropTall implements IGenericBlock, IPlantable {
    private HashSet<IBlockState> neededBlocks = new HashSet<>();

    //Block version
    public BlockGenericCropTallRestricted(String c, int meta, int cMin, int cMax, String blockId, int blockMeta) {
        super(c, meta, cMin, cMax);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
        IBlockState blockState = block == null ? null : block.getStateFromMeta(blockMeta);
        neededBlocks.add(blockState);
    }

    //Oredict version
    public BlockGenericCropTallRestricted(String c, int meta, int cMin, int cMax, String oreDictName) {
        super(c, meta, cMin, cMax);


        String cleanedOreDictName = oreDictName.startsWith("ore:") ? oreDictName.substring(4) : oreDictName;
        for (ItemStack stack : OreDictionary.getOres(cleanedOreDictName)) {
            if (stack.getItem() instanceof ItemBlock && !stack.isEmpty()) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();

                for (IBlockState state : block.getBlockState().getValidStates()) {
                    if (stack.getMetadata() == OreDictionary.WILDCARD_VALUE || stack.getMetadata() == state.getBlock().getMetaFromState(state)) {
                        neededBlocks.add(state);
                    }
                }
            }
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {

        if (!hasNeededBlock(world, pos)) return false;

        if (state.getValue(TOP)) {
            IBlockState belowState = world.getBlockState(pos.down());
            return belowState.getBlock() instanceof BlockGenericCropTall && !belowState.getValue(TOP);
        } else {
            return (world.getLight(pos) > 12 || world.canSeeSky(pos) || world.canSeeSky(pos.up()))
                    && (canBePlantedHere(world, pos) || canPlantGrowOnBlock(world.getBlockState(pos.down())));
        }
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
                    world.setBlockState(pos, state.withProperty(AGE, age + 1));
            }
        } else if (age == 7 && world.isAirBlock(up) && !isTop && hasNeededBlock(world, pos.up())) {
            if (world.getLightFromNeighbors(up) > 12) {
                if (rand.nextInt(MathHelper.ceil(growthChance * 2)) == 0)
                    world.setBlockState(up, state.withProperty(AGE, 0).withProperty(TOP, true));
            }
        }
    }

    public boolean hasNeededBlock(World worldIn, BlockPos pos) {
        return (neededBlocks.contains(worldIn.getBlockState(pos.north())) ||
                neededBlocks.contains(worldIn.getBlockState(pos.south())) ||
                neededBlocks.contains(worldIn.getBlockState(pos.east())) ||
                neededBlocks.contains(worldIn.getBlockState(pos.west())));
    }
}
