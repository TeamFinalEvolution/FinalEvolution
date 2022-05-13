package dk.zakariassen.finalevolution.common.blocks.blockEntities;

import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.common.capabilities.DimensionalEnergyConnectionMode;
import dk.zakariassen.finalevolution.common.capabilities.DimensionalEnergyConnector;
import dk.zakariassen.finalevolution.common.setup.BlockEntityTypes;
import dk.zakariassen.finalevolution.common.setup.Blocks;
import dk.zakariassen.finalevolution.common.util.EnergyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ReceiverPlugBlockEntity extends BasePlugBlockEntity {
    public static final Component TITLE = new TranslatableComponent("container." + FinalEvolution.MOD_ID + ".transmitter_plug");

    private final LazyOptional<IEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> new DimensionalEnergyConnector(DimensionalEnergyConnectionMode.RECEIVER));

    public ReceiverPlugBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.RECEIVER_PLUG.get(), blockPos, blockState);
    }

    public static BlockEntityType<ReceiverPlugBlockEntity> typeSupplier() {
        return BlockEntityType.Builder.of(ReceiverPlugBlockEntity::new, Blocks.RECEIVER_PLUG.get()).build(null);
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyCapability() {
        return this.energyStorageLazyOptional;
    }

    @Override
    public <T extends BlockEntity> void handleTick(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return;
        }

        for (Direction direction : Direction.values()) {
            BlockEntity blockEntity = level.getBlockEntity(this.getBlockPos().relative(direction));

            if (blockEntity == null) {
                continue;
            }

            blockEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(energyStorage -> {
                // Avoid loops - TODO: Implement channel logic
                if (energyStorage instanceof DimensionalEnergyConnector) {
                    return;
                }

                EnergyUtil.transferBetween(this.getEnergyStorage(), energyStorage);
            });
        }
    }
}
