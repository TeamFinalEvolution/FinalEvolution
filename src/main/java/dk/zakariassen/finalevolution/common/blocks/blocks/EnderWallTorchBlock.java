package dk.zakariassen.finalevolution.common.blocks.blocks;

import dk.zakariassen.finalevolution.common.setup.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class EnderWallTorchBlock extends WallTorchBlock {
    public EnderWallTorchBlock() {
        super(
                Properties.of(Material.DECORATION)
                        .noCollission()
                        .instabreak()
                        .lightLevel((lightLevel) -> 7)
                        .sound(SoundType.METAL)
                        .dropsLike(Blocks.ENDER_TORCH.get()),
                null
        );
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        // Do nothing
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        super.entityInside(blockState, level, blockPos, entity);

        System.out.println("entityInside! WALL");
    }


}
