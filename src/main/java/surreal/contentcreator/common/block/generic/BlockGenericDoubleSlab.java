package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.util.GeneralUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockGenericDoubleSlab extends BlockGenericSlab {
    private final Block halfSlab;

    public BlockGenericDoubleSlab(BlockGenericHalfSlab halfSlab, Material p_i47249_1_, MapColor p_i47249_2_) {
        super(p_i47249_1_, p_i47249_2_);
        this.halfSlab = halfSlab;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Nonnull
    @Override
    public Block setSoundType(@Nonnull SoundType soundType) {
        return super.setSoundType(soundType);
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(halfSlab);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return new ItemStack(halfSlab);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return halfSlab.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {

        return halfSlab.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public String getModelLocation() {
        return "cube_column";
    }

    @Override
    public void setTextures(Block block, JsonObject textures) {
        String name = getTextureName(block);
        textures.addProperty("end", name);
        textures.addProperty("side", name);
    }

    @Override
    public void setVariants(Block block, JsonObject variants) {
        JsonObject emptyObject = new JsonObject();
        JsonArray emptyArray = new JsonArray();
        emptyArray.add(new JsonObject());

        JsonObject half = new JsonObject();
        half.add("bottom", emptyObject);
        half.add("top", emptyObject);

        variants.add("half", half);

        JsonObject variant = new JsonObject();
        variant.add("normal", emptyObject);
        variants.add("variant", variant);

        variants.add("inventory", emptyArray);
    }
}
