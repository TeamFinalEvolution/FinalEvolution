package dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy;

import dk.zakariassen.finalevolution.FinalEvolution;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DimensionalEnergyManager implements INBTSerializable<CompoundTag> {
    private static DimensionalEnergyManager INSTANCE;

    private final Map<String, DimensionalEnergyStorage> energyStorages = new HashMap<>();

    private int energy = 0;
    private int capacity = 0;

    public static DimensionalEnergyManager getInstance() {
        if (DimensionalEnergyManager.INSTANCE == null) {
            DimensionalEnergyManager.INSTANCE = new DimensionalEnergyManager();
        }

        return DimensionalEnergyManager.INSTANCE;
    }

    public static void invalidate() {
        DimensionalEnergyManager.INSTANCE = null;
    }

    public void registerEnergyStorage(String uuid, DimensionalEnergyStorage dimensionalEnergyStorage) {
        if (dimensionalEnergyStorage == null) {
            return;
        }

        this.energyStorages.put(uuid, dimensionalEnergyStorage);
        this.updateCapacity(dimensionalEnergyStorage.getMaxEnergyStored());
        this.updateEnergy(dimensionalEnergyStorage.getEnergyStored());
    }

    public void deregisterEnergyStorage(String uuid) {
        DimensionalEnergyStorage dimensionalEnergyStorage = this.energyStorages.remove(uuid);

        if (dimensionalEnergyStorage == null) {
            return;
        }

        this.updateEnergy(dimensionalEnergyStorage.getEnergyStored());
        this.updateCapacity(dimensionalEnergyStorage.getMaxEnergyStored());
    }

    private void updateEnergy(int energy) {
        try {
            this.energy += Math.addExact(this.energy, energy);
        } catch (ArithmeticException exception) {
            this.energy = Integer.MAX_VALUE;
        }

        // This should never happen, but better safe than sorry
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    private void updateCapacity(int capacity) {
        try {
            this.capacity += Math.addExact(this.capacity, capacity);
        } catch (ArithmeticException exception) {
            this.capacity = Integer.MAX_VALUE;
        }

        // This should never happen, but better safe than sorry
        if (this.capacity < 0) {
            this.capacity = 0;
        }
    }

    public Optional<DimensionalEnergyStorage> getEnergyStorage(String uuid) {
        return Optional.ofNullable(this.energyStorages.get(uuid));
    }

    public boolean hasEnergyStorage(String uuid) {
        return this.energyStorages.containsKey(uuid);
    }

    public boolean isMissingEnergyStorage(String uuid) {
        return ! this.hasEnergyStorage(uuid);
    }

    public int receiveEnergy(int maxReceive, boolean simulate) {
        int totalReceived = 0;

        for (DimensionalEnergyStorage dimensionalEnergyStorage : this.energyStorages.values()) {
            if (totalReceived >= maxReceive) {
                break;
            }

            totalReceived += dimensionalEnergyStorage.receiveEnergy(maxReceive - totalReceived, simulate);
        }

        if (! simulate) {
            this.updateEnergy(totalReceived);
        }

        if (totalReceived > maxReceive) {
            FinalEvolution.LOGGER.error("Somehow received more energy than possible: max ["+maxReceive+"] received ["+totalReceived+"]");
        }

        return totalReceived;
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        int totalExtracted = 0;

        for (DimensionalEnergyStorage dimensionalEnergyStorage : this.energyStorages.values()) {
            if (totalExtracted >= maxExtract) {
                break;
            }

            totalExtracted += dimensionalEnergyStorage.extractEnergy(maxExtract - totalExtracted, simulate);
        }

        if (! simulate) {
            this.updateEnergy(-totalExtracted);
        }

        if (totalExtracted > maxExtract) {
            FinalEvolution.LOGGER.error("Somehow extracted more energy than possible: max ["+maxExtract+"] extracted ["+totalExtracted+"]");
        }

        return totalExtracted;
    }

    public int getEnergyStored() {
        return this.energy;
    }

    public int getMaxEnergyStored() {
        return this.capacity;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        this.energyStorages.forEach((key, value) -> tag.put(key, value.serializeNBT()));

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        nbt.getAllKeys().forEach(key -> this.registerEnergyStorage(key, DimensionalEnergyStorage.fromNBT(nbt.get(key))));
    }

    public DimensionalEnergyStorage getOrCreate(boolean register, String uuid, int capacity) {
        DimensionalEnergyStorage storage = this.energyStorages.get(uuid);

        // get
        if (storage != null) {
            return storage;
        }

        // or create
        storage = new DimensionalEnergyStorage(capacity);

        if (register) {
            this.registerEnergyStorage(uuid, storage);
        }

        return storage;
    }
}
