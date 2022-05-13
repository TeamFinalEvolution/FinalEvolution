package dk.zakariassen.finalevolution.common.capabilities;

import dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy.DimensionalEnergyManager;
import net.minecraftforge.energy.IEnergyStorage;

public class DimensionalEnergyConnector implements IEnergyStorage {
    private final DimensionalEnergyConnectionMode mode;

    public DimensionalEnergyConnector(DimensionalEnergyConnectionMode mode) {
        this.mode = mode;
    }

    @Override
    public boolean canExtract() {
        return this.mode == DimensionalEnergyConnectionMode.RECEIVER;
    }

    @Override
    public boolean canReceive() {
        return this.mode == DimensionalEnergyConnectionMode.TRANSMITTER;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (! this.canReceive()) {
            return 0;
        }

        return DimensionalEnergyManager.getInstance().receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (! this.canExtract()) {
            return 0;
        }

        return DimensionalEnergyManager.getInstance().extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return DimensionalEnergyManager.getInstance().getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return DimensionalEnergyManager.getInstance().getMaxEnergyStored();
    }
}
