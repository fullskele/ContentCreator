package surreal.contentcreator.common.block.generic;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import surreal.contentcreator.types.CTSoundType;

import javax.annotation.Nullable;
import java.util.Random;

// idk why i added this. We might see a funny pack using this lol
@SuppressWarnings("all")
public class BlockGenericCocoa extends BlockCocoa implements IGenericBlock {
    private boolean cropItemExists;
    private String cropID;
    private int cropMeta;
    private ItemStack crop;
    private int cMin;

    @Nullable
    private ItemStack tryGetCrop(){
        if (crop == null && cropItemExists) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cropID));
            if (item != null) crop = new ItemStack(item, 1, cropMeta);
            if (crop == null) cropItemExists = false;
        }
        return crop;
    }

    public BlockGenericCocoa(String c, int meta, int cMin) {
        cropItemExists = true;
        cropID = c;
        cropMeta = meta;

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cropID));
        ItemStack itemStack = item == null ? null : new ItemStack(item, 1, cropMeta);
        crop = itemStack == null ? null : itemStack;

        this.cMin = Math.max(cMin, 1);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return tryGetCrop();
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(AGE) >= 2 ? Math.max(1, random.nextInt(cMin)) * Math.max(1, fortune) + tryGetCrop().getCount() : 1;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return tryGetCrop().getItem();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return tryGetCrop().getMetadata();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        int count = quantityDropped(state, fortune, rand);

        ItemStack itemStack = tryGetCrop();
        if (itemStack != null)
            for (int i = 0; i < count; i++)
                drops.add(itemStack.copy());
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }
}
