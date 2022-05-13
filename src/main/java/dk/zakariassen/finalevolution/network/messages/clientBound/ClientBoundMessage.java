package dk.zakariassen.finalevolution.network.messages.clientBound;

import dk.zakariassen.finalevolution.network.FinalEvolutionNetwork;
import dk.zakariassen.finalevolution.network.messages.IMessage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

abstract public class ClientBoundMessage implements IMessage {
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FinalEvolutionNetwork.CLIENT_MESSAGE_HANDLER.handlePacket(this, context));
        });

        context.get().setPacketHandled(true);
    }
}
