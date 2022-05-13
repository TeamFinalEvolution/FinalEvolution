package dk.zakariassen.finalevolution.network.messages.serverBound;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.simple.SimpleChannel;

public class ServerBoundTestBoxGetEnergyRequestMessage extends ServerBoundMessage {
    public final BlockPos blockPos;

    public ServerBoundTestBoxGetEnergyRequestMessage(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.blockPos);
    }

    public static ServerBoundTestBoxGetEnergyRequestMessage decode(FriendlyByteBuf buffer) {
        return new ServerBoundTestBoxGetEnergyRequestMessage(buffer.readBlockPos());
    }

    public static void register(SimpleChannel channel, int id) {
        channel.registerMessage(
                id,
                ServerBoundTestBoxGetEnergyRequestMessage.class,
                ServerBoundTestBoxGetEnergyRequestMessage::encode,
                ServerBoundTestBoxGetEnergyRequestMessage::decode,
                ServerBoundTestBoxGetEnergyRequestMessage::handle
        );
    }
}
