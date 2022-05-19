package surreal.contentcreator.functions.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("contentcreator.block.functions.IFaceShape")
public interface IBlockFaceShapeFunc {
    String getBlockFaceShape(IBlockAccess world, IBlockState state, IBlockPos pos, IFacing facing);
}
