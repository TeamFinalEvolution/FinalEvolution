package dk.zakariassen.finalevolution.network.handlers;

import dk.zakariassen.finalevolution.common.blocks.blockEntities.DimensionalEnergyStorageBlockEntity;
import dk.zakariassen.finalevolution.common.setup.BlockEntityTypes;
import dk.zakariassen.finalevolution.network.messages.clientBound.ClientBoundTestBoxEnergyUpdateMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessageHandler extends MessageHandler {
    public static void handle(ClientBoundTestBoxEnergyUpdateMessage message, Supplier<NetworkEvent.Context> context) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level == null) {
            return;
        }

        level.getBlockEntity(message.blockPos, BlockEntityTypes.DIMENSIONAL_ENERGY_STORAGE.get()).ifPresent((DimensionalEnergyStorageBlockEntity dimensionalEnergyStorageBlockEntity) -> {
            // Den overrider blokkens storage, men opdatere ikke global handler stuff
            dimensionalEnergyStorageBlockEntity.getEnergyStorage().deserializeNBT(message.energyStorageTag);
        });
    }
}
