package dk.zakariassen.finalevolution.common.blocks.blockEntities;

import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.common.blocks.base.BaseBlockEntity;
import dk.zakariassen.finalevolution.common.capabilities.DimensionalEnergyConnectionMode;
import dk.zakariassen.finalevolution.common.capabilities.DimensionalEnergyConnector;
import dk.zakariassen.finalevolution.common.capabilities.ReactiveEnergyStorage;
import dk.zakariassen.finalevolution.common.setup.BlockEntityTypes;
import dk.zakariassen.finalevolution.common.setup.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TransmitterPlugBlockEntity extends BasePlugBlockEntity {
    public static final Component TITLE = new TranslatableComponent("container." + FinalEvolution.MOD_ID + ".transmitter_plug");

    private final LazyOptional<IEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> new DimensionalEnergyConnector(DimensionalEnergyConnectionMode.TRANSMITTER));

    public TransmitterPlugBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.TRANSMITTER_PLUG.get(), blockPos, blockState);
    }

    public static BlockEntityType<TransmitterPlugBlockEntity> typeSupplier() {
        return BlockEntityType.Builder.of(TransmitterPlugBlockEntity::new, Blocks.TRANSMITTER_PLUG.get()).build(null);
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
    }
}
