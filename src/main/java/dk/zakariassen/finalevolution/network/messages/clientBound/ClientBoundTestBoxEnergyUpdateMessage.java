package dk.zakariassen.finalevolution.network.messages.clientBound;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.simple.SimpleChannel;

public class ClientBoundTestBoxEnergyUpdateMessage extends ClientBoundMessage {
    public final Tag energyStorageTag;
    public final BlockPos blockPos;

    public ClientBoundTestBoxEnergyUpdateMessage(Tag energyStorageTag, BlockPos blockPos) {
        this.energyStorageTag = energyStorageTag;
        this.blockPos = blockPos;
    }

    public void encode(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        tag.put("energy", this.energyStorageTag);

        buffer.writeNbt(tag);
        buffer.writeBlockPos(this.blockPos);
    }

    public static ClientBoundTestBoxEnergyUpdateMessage decode(FriendlyByteBuf buffer) {
        return new ClientBoundTestBoxEnergyUpdateMessage(buffer.readNbt().get("energy"), buffer.readBlockPos());
    }

    public static void register(SimpleChannel channel, int id) {
        channel.registerMessage(
                id,
                ClientBoundTestBoxEnergyUpdateMessage.class,
                ClientBoundTestBoxEnergyUpdateMessage::encode,
                ClientBoundTestBoxEnergyUpdateMessage::decode,
                ClientBoundTestBoxEnergyUpdateMessage::handle
        );
    }
}
