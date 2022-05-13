package dk.zakariassen.finalevolution.common.blocks.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class EnderTorchBlock extends TorchBlock {
    public EnderTorchBlock() {
        super(
                BlockBehaviour.Properties.of(Material.DECORATION)
                        .noCollission()
                        .instabreak()
                        .lightLevel((lightLevel) -> 7)
                        .sound(SoundType.METAL),
                null
        );
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        // Do nothing
    }
}
