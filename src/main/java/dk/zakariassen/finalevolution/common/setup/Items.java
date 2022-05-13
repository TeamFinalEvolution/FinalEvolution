package dk.zakariassen.finalevolution.common.setup;

import dk.zakariassen.finalevolution.FinalEvolution;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class Items {

    static void register(IEventBus modEventBus) {
        Registration.ITEMS.register(modEventBus);
    }

        public static final RegistryObject<Item> ENDER_TORCH = Registration.ITEMS.register("ender_torch", () -> new StandingAndWallBlockItem(Blocks.ENDER_TORCH.get(), Blocks.ENDER_WALL_TORCH.get(), (new Item.Properties()).tab(FinalEvolution.TAB)));

// Reference:
//    public static final RegistryObject<Item> CHUNK_CONTROLLER = Registration.ITEMS.register("chunk_controller", () -> new APItem(new Item.Properties().stacksTo(16),
//            CCRegistration.ID.CHUNKY_TURTLE, null, APConfig.PERIPHERALS_CONFIG.ENABLE_CHUNKY_TURTLE::get));
}
