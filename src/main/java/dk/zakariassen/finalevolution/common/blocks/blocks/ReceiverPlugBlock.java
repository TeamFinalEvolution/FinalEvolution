package dk.zakariassen.finalevolution.common.blocks.blocks;

import dk.zakariassen.finalevolution.common.blocks.blockEntities.ReceiverPlugBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ReceiverPlugBlock extends BasePlugBlock {
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ReceiverPlugBlockEntity(blockPos, blockState);
    }
}
