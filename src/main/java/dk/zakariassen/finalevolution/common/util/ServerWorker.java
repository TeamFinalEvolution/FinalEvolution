package dk.zakariassen.finalevolution.common.util;

import dk.zakariassen.finalevolution.FinalEvolution;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayDeque;
import java.util.Queue;

@Mod.EventBusSubscriber(modid = FinalEvolution.MOD_ID)
public class ServerWorker {

    private static final Queue<Runnable> callQueue = new ArrayDeque<>();

    public static void add(final Runnable call) {
        callQueue.add(call);
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            while (!callQueue.isEmpty()) {
                final Runnable runnable = callQueue.poll();
                runnable.run();
            }
        }
    }
}
