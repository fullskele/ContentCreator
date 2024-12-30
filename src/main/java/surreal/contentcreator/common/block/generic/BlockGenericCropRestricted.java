package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;

public class BlockGenericCropRestricted extends BlockGenericCrop implements IGenericBlock {
    private HashSet<IBlockState> neededBlocks = new HashSet<>();


    //Block version
    public BlockGenericCropRestricted(String c, int meta, int cMin, int cMax, String blockId, int blockMeta) {
        super(c, meta, cMin, cMax);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
        IBlockState blockState = block == null ? null : block.getStateFromMeta(blockMeta);
        neededBlocks.add(blockState);
    }


    //Oredict version
    public BlockGenericCropRestricted(String c, int meta, int cMin, int cMax, String oreDictName) {
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
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state ){
        IBlockState soil = worldIn.getBlockState(pos.down());
        if (hasNeededBlock(worldIn, pos)) return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
        return false;
    }

    public boolean hasNeededBlock(World worldIn, BlockPos pos) {
        return (neededBlocks.contains(worldIn.getBlockState(pos.north())) ||
                neededBlocks.contains(worldIn.getBlockState(pos.south())) ||
                neededBlocks.contains(worldIn.getBlockState(pos.east())) ||
                neededBlocks.contains(worldIn.getBlockState(pos.west())));
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return !this.isMaxAge(state) &&  hasNeededBlock(worldIn, pos);
    }

}
