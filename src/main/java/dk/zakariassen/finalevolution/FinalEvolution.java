package dk.zakariassen.finalevolution;

import com.minecolonies.api.items.ModItems;
import dk.zakariassen.finalevolution.common.globalState.DimensionalEnergy.DimensionalEnergyManager;
import dk.zakariassen.finalevolution.common.setup.Blocks;
import dk.zakariassen.finalevolution.common.setup.Registration;
import dk.zakariassen.finalevolution.network.FinalEvolutionNetwork;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(FinalEvolution.MOD_ID)
public class FinalEvolution {

    public static final String MOD_ID = "finalevolution";
    public static final Logger LOGGER = LogManager.getLogger("Final Evolution");
    public static final CreativeModeTab TAB = new CreativeModeTab("finalevolutiontab") {
        @Override
        @NotNull
        public ItemStack makeIcon() {
            return ModItems.chorusBread.getDefaultInstance();
        }
    };

    public FinalEvolution() {
        LOGGER.info("Final Evolution says hello!");
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::interModComms);
        modBus.addListener(this::clientSetup);

        Registration.register();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FinalEvolutionNetwork.init();
        });
    }

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(Blocks.DIMENSIONAL_ENERGY_STORAGE.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(Blocks.ENDER_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(Blocks.ENDER_WALL_TORCH.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public void interModComms(InterModEnqueueEvent event) {

    }

    @SubscribeEvent
    public void serverStopped(ServerStoppedEvent event) {
        DimensionalEnergyManager.invalidate();
    }

}