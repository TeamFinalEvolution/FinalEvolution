package dk.zakariassen.finalevolution.network;

import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.network.handlers.ClientMessageHandler;
import dk.zakariassen.finalevolution.network.handlers.MessageHandler;
import dk.zakariassen.finalevolution.network.handlers.ServerMessageHandler;
import dk.zakariassen.finalevolution.network.messages.clientBound.ClientBoundTestBoxEnergyUpdateMessage;
import dk.zakariassen.finalevolution.network.messages.serverBound.ServerBoundTestBoxGetEnergyRequestMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class FinalEvolutionNetwork {
    private static final String PROTOCOL_VERSION = "1";

    private static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FinalEvolution.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static final MessageHandler CLIENT_MESSAGE_HANDLER = new ClientMessageHandler();
    public static final MessageHandler SERVER_MESSAGE_HANDLER = new ServerMessageHandler();

    private static int id = 0;

    public static void init() {
        ClientBoundTestBoxEnergyUpdateMessage.register(NETWORK_CHANNEL, ++id);
        ServerBoundTestBoxGetEnergyRequestMessage.register(NETWORK_CHANNEL, ++id);
    }

    /**
     * Sends a packet to the server.<p>
     * Must be called Client side.
     */
    public static void sendToServer(Object msg) {
        NETWORK_CHANNEL.sendToServer(msg);
    }

    /**
     * Send a packet to a specific player.<p>
     * Must be called Server side.
     */
    public static void sendTo(ServerPlayer player, Object msg) {
        if (!(player instanceof FakePlayer)) {
            NETWORK_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    public static void sendToAll(Object packet) {
        NETWORK_CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static ClientboundBlockEntityDataPacket createTEUpdatePacket(BlockEntity blockEntity) {
        return ClientboundBlockEntityDataPacket.create(blockEntity);
    }

    public static void sendToAllAround(Object mes, ResourceKey<Level> dim, BlockPos pos, int radius) {
        NETWORK_CHANNEL.send(PacketDistributor.NEAR
                .with(() -> new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), radius, dim)), mes);
    }

    public static void sendToAllInWorld(Object mes, ServerLevel world) {
        NETWORK_CHANNEL.send(PacketDistributor.DIMENSION.with(world::dimension), mes);
    }
}
