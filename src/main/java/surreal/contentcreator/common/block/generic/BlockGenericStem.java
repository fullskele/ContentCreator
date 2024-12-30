package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import surreal.contentcreator.types.CTSoundType;

import javax.annotation.Nullable;

public class BlockGenericStem extends BlockStem implements IGenericBlock {

    private boolean cropItemExists;
    private String cropID;
    private int cropMeta;
    private ItemStack seed;
    private IBlockState crop;

    @Nullable
    private IBlockState tryGetCrop() {
        if (crop == null && cropItemExists) {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(cropID));
            if (block != null) crop = block.getStateFromMeta(cropMeta);
            if (crop == null) cropItemExists = false;
        }
        return crop;
    }

    public static BlockGenericStem create(String c, int meta) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(c));
        IBlockState blockState = block == null ? null : block.getStateFromMeta(meta);
        return new BlockGenericStem(blockState, c, meta);
    }

    public BlockGenericStem(IBlockState crop, String c, int meta) {
        super(crop.getBlock());
        this.cropItemExists = true;
        this.cropID = c;
        this.cropMeta = meta;
        this.crop = crop;
    }

    @Override
    protected Item getSeedItem() {
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
