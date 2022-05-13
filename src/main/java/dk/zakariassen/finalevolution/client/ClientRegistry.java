package dk.zakariassen.finalevolution.client;

import dk.zakariassen.finalevolution.FinalEvolution;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FinalEvolution.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry {

    @SubscribeEvent
    public static void onModelBakeEvent(ModelRegistryEvent event) {

    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        KeyBindings.register();
    }
}