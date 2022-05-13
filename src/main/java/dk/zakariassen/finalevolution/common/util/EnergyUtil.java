package dk.zakariassen.finalevolution.common.util;

import dk.zakariassen.finalevolution.FinalEvolution;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyUtil {
    public static int transferBetween(IEnergyStorage from, IEnergyStorage to) {
        return EnergyUtil.transferBetween(from, to, from.getEnergyStored());
    }

    public static int transferBetween(IEnergyStorage from, IEnergyStorage to, int maxExtract) {
        // Cannot transfer from itself to itself
        if (from == to) {
            return 0;
        }

        // Transfer is not possible in the given direction
        if (!from.canExtract() || !to.canReceive()) {
            return 0;
        }

        // No capacity for transfer
        if (from.getEnergyStored() <= 0 || to.getEnergyStored() == to.getMaxEnergyStored()) {
            return 0;
        }

        int maxActualTransfer = from.extractEnergy(maxExtract, true);
        int actualTransferReceive = to.receiveEnergy(maxActualTransfer, false);
        int actualTransferExtract = from.extractEnergy(actualTransferReceive, false);

        if (actualTransferReceive != actualTransferExtract) {
            FinalEvolution.LOGGER.error("EnergyUtil::transferBetween mismatch between actual transfer amounts");
        }

        return actualTransferReceive;
    }
}
