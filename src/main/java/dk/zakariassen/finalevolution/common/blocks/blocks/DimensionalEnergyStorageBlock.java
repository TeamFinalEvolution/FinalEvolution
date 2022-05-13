package dk.zakariassen.finalevolution.common.blocks.blocks;

import com.ldtteam.domumornamentum.block.AbstractBlock;
import dk.zakariassen.finalevolution.client.screens.EnergyStorageScreen;
import dk.zakariassen.finalevolution.common.blocks.base.BaseEntityBlock;
import dk.zakariassen.finalevolution.common.blocks.blockEntities.DimensionalEnergyStorageBlockEntity;
import dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy.DimensionalEnergyManager;
import dk.zakariassen.finalevolution.common.setup.BlockEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DimensionalEnergyStorageBlock extends BaseEntityBlock {

    public DimensionalEnergyStorageBlock() {
        super(AbstractBlock.Properties.of(Material.STONE).strength(2).noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DimensionalEnergyStorageBlockEntity(blockPos, blockState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide && level.getBlockEntity(pos) instanceof final DimensionalEnergyStorageBlockEntity dimensionalEnergyStorageBlockEntity) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().setScreen(new EnergyStorageScreen(dimensionalEnergyStorageBlockEntity)));
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void afterDestroyedByPlayer(BlockEntity blockEntity, BlockState state, Level level, BlockPos blockPos, Player player, boolean willHarvest, FluidState fluid) {
        if (level.isClientSide()) {
            return;
        }

        if (blockEntity instanceof DimensionalEnergyStorageBlockEntity energyStorageBlockEntity) {
            DimensionalEnergyManager.getInstance().deregisterEnergyStorage(energyStorageBlockEntity.getDimensionalEnergyUuid());
        }
    }
}
