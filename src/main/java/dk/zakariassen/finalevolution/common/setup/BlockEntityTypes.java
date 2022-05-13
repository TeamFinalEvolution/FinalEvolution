package dk.zakariassen.finalevolution.common.setup;


import dk.zakariassen.finalevolution.common.blocks.blockEntities.DimensionalEnergyStorageBlockEntity;
import dk.zakariassen.finalevolution.common.blocks.blockEntities.ReceiverPlugBlockEntity;
import dk.zakariassen.finalevolution.common.blocks.blockEntities.TransmitterPlugBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypes {
    static void register(IEventBus modEventBus) {
        Registration.BLOCK_ENTITIES.register(modEventBus);
    }

    // Reference code
    public static final RegistryObject<BlockEntityType<DimensionalEnergyStorageBlockEntity>> DIMENSIONAL_ENERGY_STORAGE = Registration.BLOCK_ENTITIES.register("dimensional_energy_storage", DimensionalEnergyStorageBlockEntity::typeSupplier);
    public static final RegistryObject<BlockEntityType<TransmitterPlugBlockEntity>> TRANSMITTER_PLUG = Registration.BLOCK_ENTITIES.register("transmitter_plug", TransmitterPlugBlockEntity::typeSupplier);
    public static final RegistryObject<BlockEntityType<ReceiverPlugBlockEntity>> RECEIVER_PLUG = Registration.BLOCK_ENTITIES.register("receiver_plug", ReceiverPlugBlockEntity::typeSupplier);
}