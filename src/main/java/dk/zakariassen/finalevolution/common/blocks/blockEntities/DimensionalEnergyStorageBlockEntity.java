package dk.zakariassen.finalevolution.common.blocks.blockEntities;

import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.common.blocks.base.BaseBlockEntity;
import dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy.DimensionalEnergyManager;
import dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy.DimensionalEnergyStorage;
import dk.zakariassen.finalevolution.common.setup.BlockEntityTypes;
import dk.zakariassen.finalevolution.common.setup.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DimensionalEnergyStorageBlockEntity extends BaseBlockEntity {
    public static final Component TITLE = new TranslatableComponent("container." + FinalEvolution.MOD_ID + ".dimensional_energy_storage");

    private final LazyOptional<IEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> DimensionalEnergyManager.getInstance().getOrCreate(!this.level.isClientSide(), this.dimensionalEnergyUuid, 10000));

    private String dimensionalEnergyUuid;

    public DimensionalEnergyStorageBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.DIMENSIONAL_ENERGY_STORAGE.get(), blockPos, blockState);
    }

    public static BlockEntityType<DimensionalEnergyStorageBlockEntity> typeSupplier() {
        return BlockEntityType.Builder.of(DimensionalEnergyStorageBlockEntity::new, Blocks.DIMENSIONAL_ENERGY_STORAGE.get()).build(null);
    }

    public String getDimensionalEnergyUuid() {
        return this.dimensionalEnergyUuid;
    }

    public DimensionalEnergyStorage getEnergyStorage() {
        //noinspection OptionalGetWithoutIsPresent
        return (DimensionalEnergyStorage) this.energyStorageLazyOptional.resolve().get();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        tag.put("uuid", StringTag.valueOf(this.dimensionalEnergyUuid));
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        Tag uuidTag = tag.get("uuid");

        if (uuidTag != null) {
            this.dimensionalEnergyUuid = uuidTag.getAsString();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // If the entity does not have an uuid, then assign it one
        if (this.dimensionalEnergyUuid == null) {
            this.dimensionalEnergyUuid = UUID.randomUUID().toString();
        }

        // If the associated uuid is not registered within the manager,
        // then resolve the energy storage to auto register it.
        if (DimensionalEnergyManager.getInstance().isMissingEnergyStorage(this.dimensionalEnergyUuid)) {
            this.energyStorageLazyOptional.resolve();
        }
    }
}
