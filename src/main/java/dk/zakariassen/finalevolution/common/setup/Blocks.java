package dk.zakariassen.finalevolution.common.setup;

import dk.zakariassen.finalevolution.common.blocks.blocks.*;
import dk.zakariassen.finalevolution.common.items.base.BaseEnabledBlockItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Blocks {

    static void register(IEventBus modEventBus) {
        Registration.BLOCKS.register(modEventBus);
    }

    public static final RegistryObject<Block> DIMENSIONAL_ENERGY_STORAGE = Blocks.register(
            "dimensional_energy_storage",
            DimensionalEnergyStorageBlock::new,
            () -> new BaseEnabledBlockItem(Blocks.DIMENSIONAL_ENERGY_STORAGE.get())
    );

    public static final RegistryObject<Block> TRANSMITTER_PLUG = Blocks.register(
            "transmitter_plug",
            TransmitterPlugBlock::new,
            () -> new BaseEnabledBlockItem(Blocks.TRANSMITTER_PLUG.get())
    );

    public static final RegistryObject<Block> RECEIVER_PLUG = Blocks.register(
            "receiver_plug",
            ReceiverPlugBlock::new,
            () -> new BaseEnabledBlockItem(Blocks.RECEIVER_PLUG.get())
    );

    public static final RegistryObject<Block> ENDER_TORCH = Blocks.registerNoItem(
            "ender_torch",
            EnderTorchBlock::new
    );

    public static final RegistryObject<Block> ENDER_WALL_TORCH = Blocks.registerNoItem(
            "ender_wall_torch",
            EnderWallTorchBlock::new
    );

    // Reference code
//    public static final RegistryObject<Block> TEST_BOX = Blocks.register(
//            "chat_box",
//            () -> new APBlockEntityBlock<>(BlockEntityTypes.CHAT_BOX, true),
//            () -> new BaseEnabledBlockItem(Blocks.TEST_BOX.get())
//    );

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Supplier<BlockItem> blockItem) {
        RegistryObject<T> registryObject = Blocks.registerNoItem(name, block);
        Registration.ITEMS.register(name, blockItem);

        return registryObject;
    }
}