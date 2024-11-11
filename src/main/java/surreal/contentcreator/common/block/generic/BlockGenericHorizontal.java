package surreal.contentcreator.common.block.generic;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import surreal.contentcreator.types.CTSoundType;

public class BlockGenericHorizontal extends BlockHorizontal implements IGenericBlock {
    protected BlockGenericHorizontal(Material materialIn, MapColor colorIn) {
        super(materialIn, colorIn);
    }

    protected BlockGenericHorizontal(Material materialIn) {
        super(materialIn);
    }

    @Override
    public void setTextures(Block block, JsonObject textures) {
        String name = getTextureName(block);
        textures.addProperty("top", name);
        textures.addProperty("bottom", name);
        textures.addProperty("side", name);
        textures.addProperty("front", name);
    }

    @Override
    public void setSoundType(CTSoundType soundType) {
        setSoundType(soundType.getType());
    }


    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
}