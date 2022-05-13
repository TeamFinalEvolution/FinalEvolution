package dk.zakariassen.finalevolution.common.capabilities;

import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;

public class ReactiveEnergyStorage extends EnergyStorage {
    private final ArrayList<Runnable> listeners = new ArrayList<>();

    public ReactiveEnergyStorage(int capacity) {
        super(capacity);
    }

    public ReactiveEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ReactiveEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public ReactiveEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int result = super.receiveEnergy(maxReceive, simulate);

        if (result != 0) {
            this.runListeners();
        }

        return result;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int result = super.extractEnergy(maxExtract, simulate);

        if (result != 0) {
            this.runListeners();
        }

        return result;
    }

    public int transferEnergyTo(IEnergyStorage other) {
        return this.transferEnergyTo(other, this.maxExtract);
    }

    public int transferEnergyTo(IEnergyStorage other, int amount) {
        // If transferring energy to itself
        if (this == other) {
            return 0;
        }

        // If transfer is not allowed
        if (! this.canExtract() || ! other.canReceive()) {
            return 0;
        }

        // If transfer is not possible
        if (this.getEnergyStored() == 0 || other.getEnergyStored() >= other.getMaxEnergyStored()) {
            return 0;
        }

        // Perform actual transfer
        int maximumExtractionAmount = this.extractEnergy(amount, true);
        int actuallyTransferred = other.receiveEnergy(maximumExtractionAmount, false);
        this.extractEnergy(actuallyTransferred, false);

        return actuallyTransferred;
    }

    public int transferEnergyFrom(IEnergyStorage other) {
        return this.transferEnergyFrom(other, this.maxReceive);
    }

    public int transferEnergyFrom(IEnergyStorage other, int amount) {
        // If transferring energy to itself
        if (this == other) {
            return 0;
        }

        // If transfer is not allowed
        if (! this.canReceive() || ! other.canExtract()) {
            return 0;
        }

        // If transfer is not possible
        if (this.getEnergyStored() == this.getMaxEnergyStored() || other.getEnergyStored() == 0) {
            return 0;
        }

        // Perform actual transfer
        int maximumExtractionAmount = other.extractEnergy(amount, true);
        int actuallyTransferred = this.receiveEnergy(maximumExtractionAmount, false);
        other.extractEnergy(actuallyTransferred, false);

        return actuallyTransferred;
    }

    public ReactiveEnergyStorage addListener(Runnable runnable) {
        this.listeners.add(runnable);

        return this;
    }

    private void runListeners() {
        for (Runnable runnable : this.listeners) {
            runnable.run();
        }
    }
}
