package dk.zakariassen.finalevolution.network.messages.serverBound;

import dk.zakariassen.finalevolution.network.FinalEvolutionNetwork;
import dk.zakariassen.finalevolution.network.messages.IMessage;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

abstract public class ServerBoundMessage implements IMessage {
    public void handle(Supplier<NetworkEvent.Context> context) {
        FinalEvolutionNetwork.SERVER_MESSAGE_HANDLER.handlePacket(this, context);

        context.get().setPacketHandled(true);
    }
}
