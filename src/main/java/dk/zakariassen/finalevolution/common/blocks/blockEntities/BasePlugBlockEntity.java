package dk.zakariassen.finalevolution.common.blocks.blockEntities;

import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.common.blocks.base.BaseBlockEntity;
import dk.zakariassen.finalevolution.common.capabilities.DimensionalEnergyConnector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract public class BasePlugBlockEntity extends BaseBlockEntity {
    public BasePlugBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    abstract public LazyOptional<IEnergyStorage> getEnergyCapability();

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY && (side == null || side == this.getBlockState().getValue(BlockStateProperties.FACING).getOpposite())) {
            return this.getEnergyCapability().cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();

        this.getEnergyCapability().invalidate();
    }

    public DimensionalEnergyConnector getEnergyStorage() {
        //noinspection OptionalGetWithoutIsPresent
        return (DimensionalEnergyConnector) this.getEnergyCapability().resolve().get();
    }
}
