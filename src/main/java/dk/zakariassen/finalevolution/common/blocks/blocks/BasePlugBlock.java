package dk.zakariassen.finalevolution.common.blocks.blocks;

import dk.zakariassen.finalevolution.common.blocks.base.BaseEntityBlock;
import dk.zakariassen.finalevolution.common.blocks.blockEntities.TransmitterPlugBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

abstract public class BasePlugBlock extends BaseEntityBlock {

    private static final VoxelShape UP_SHAPE = Block.box(3, 0, 3, 13, 8, 13);
    private static final VoxelShape DOWN_SHAPE = Block.box(3, 8, 3, 13, 16, 13);

    private static final VoxelShape EAST_SHAPE = Block.box(0, 3, 3, 8, 13, 13);
    private static final VoxelShape WEST_SHAPE = Block.box(8, 3, 3, 16, 13, 13);

    private static final VoxelShape NORTH_SHAPE = Block.box(3, 3, 8, 13, 13, 16);
    private static final VoxelShape SOUTH_SHAPE = Block.box(3, 3, 0, 13, 13, 8);

    private static final DirectionProperty FACING = BlockStateProperties.FACING;

    public BasePlugBlock() {
        super(Properties.of(Material.STONE).strength(2).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getClickedFace());
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }
}
