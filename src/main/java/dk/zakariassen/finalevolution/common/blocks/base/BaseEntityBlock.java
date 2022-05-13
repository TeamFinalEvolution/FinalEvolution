package dk.zakariassen.finalevolution.common.blocks.base;

import dk.zakariassen.finalevolution.common.blocks.blockEntities.DimensionalEnergyStorageBlockEntity;
import dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy.DimensionalEnergyManager;
import dk.zakariassen.finalevolution.common.setup.BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

abstract public class BaseEntityBlock extends BaseBlock implements EntityBlock {
    public BaseEntityBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level1, blockPos, blockState, entity) -> {
            if (entity instanceof BaseBlockEntity blockEntity) {
                blockEntity.handleTick(level, state, type);
            }
        };
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos blockPos, Player player, boolean willHarvest, FluidState fluid) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        boolean blockWasDestroyed = super.onDestroyedByPlayer(state, level, blockPos, player, willHarvest, fluid);

        if (blockWasDestroyed) {
            this.afterDestroyedByPlayer(blockEntity, state, level, blockPos, player, willHarvest, fluid);
        }

        return blockWasDestroyed;
    }

    /**
     * Is only called when the block has been successfully destroyed.
     */
    public void afterDestroyedByPlayer(@Nullable BlockEntity blockEntity, BlockState state, Level level, BlockPos blockPos, Player player, boolean willHarvest, FluidState fluid) {
        // Do nothing
    }
}
