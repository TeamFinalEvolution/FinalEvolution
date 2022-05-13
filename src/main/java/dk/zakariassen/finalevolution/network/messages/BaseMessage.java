package dk.zakariassen.finalevolution.network.messages;

import dk.zakariassen.finalevolution.network.FinalEvolutionNetwork;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

abstract public class BaseMessage implements IMessage {
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FinalEvolutionNetwork.CLIENT_MESSAGE_HANDLER.handlePacket(this, context));
            DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> FinalEvolutionNetwork.SERVER_MESSAGE_HANDLER.handlePacket(this, context));
        });

        context.get().setPacketHandled(true);
    }
}
