package dk.zakariassen.finalevolution.common.setup;

import net.minecraftforge.eventbus.api.IEventBus;

public class Containers {

    static void register(IEventBus modEventBus) {
        Registration.CONTAINER_TYPES.register(modEventBus);
    }

// Reference code:
//    public static final RegistryObject<MenuType<TestBoxContainer>> TEST_BOX = Registration.CONTAINER_TYPES.register("test_box", () -> new MenuType<>(TestBoxContainer::new));
}
