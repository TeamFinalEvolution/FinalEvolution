package dk.zakariassen.finalevolution.network.handlers;

import dk.zakariassen.finalevolution.common.blocks.blockEntities.DimensionalEnergyStorageBlockEntity;
import dk.zakariassen.finalevolution.common.setup.BlockEntityTypes;
import dk.zakariassen.finalevolution.network.FinalEvolutionNetwork;
import dk.zakariassen.finalevolution.network.messages.clientBound.ClientBoundTestBoxEnergyUpdateMessage;
import dk.zakariassen.finalevolution.network.messages.serverBound.ServerBoundTestBoxGetEnergyRequestMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerMessageHandler extends MessageHandler {
    public static void handle(ServerBoundTestBoxGetEnergyRequestMessage message, Supplier<NetworkEvent.Context> context) {
        ServerPlayer senderPlayer = context.get().getSender();

        //noinspection ConstantConditions
        senderPlayer.level
                .getBlockEntity(message.blockPos, BlockEntityTypes.DIMENSIONAL_ENERGY_STORAGE.get())
                .ifPresent((DimensionalEnergyStorageBlockEntity blockEntity) -> {
                    FinalEvolutionNetwork.sendTo(senderPlayer, new ClientBoundTestBoxEnergyUpdateMessage(blockEntity.getEnergyStorage().serializeNBT(), blockEntity.getBlockPos()));
                });
    }
}
