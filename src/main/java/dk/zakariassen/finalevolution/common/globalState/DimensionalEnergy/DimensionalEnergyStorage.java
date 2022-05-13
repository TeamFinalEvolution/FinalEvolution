package dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

public class DimensionalEnergyStorage extends EnergyStorage {
    private DimensionalEnergyStorage() {
        this(0);
    }

    public DimensionalEnergyStorage(int capacity) {
        super(capacity);
    }

    public DimensionalEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public DimensionalEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public DimensionalEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public static DimensionalEnergyStorage fromNBT(Tag tag) {
        DimensionalEnergyStorage energyStorage = new DimensionalEnergyStorage();

        energyStorage.deserializeNBT(tag);

        return energyStorage;
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.put("energy", IntTag.valueOf(this.energy));
        tag.put("capacity", IntTag.valueOf(this.capacity));
        tag.put("maxReceive", IntTag.valueOf(this.maxReceive));
        tag.put("maxExtract", IntTag.valueOf(this.maxExtract));

        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (! (nbt instanceof CompoundTag compoundTag)) {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }

        this.energy = compoundTag.getInt("energy");
        this.capacity = compoundTag.getInt("capacity");
        this.maxReceive = compoundTag.getInt("maxReceive");
        this.maxExtract = compoundTag.getInt("maxExtract");
    }
}
