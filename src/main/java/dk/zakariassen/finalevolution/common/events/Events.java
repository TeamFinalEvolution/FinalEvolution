package dk.zakariassen.finalevolution.common.events;

import dk.zakariassen.finalevolution.FinalEvolution;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FinalEvolution.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {

    private static final String PLAYED_BEFORE = "ap_played_before";

    @SubscribeEvent
    public static void onWorldJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();

        if (!hasPlayedBefore(player)) {
            ItemStack book = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("patchouli", "guide_book")));
            CompoundTag nbt = new CompoundTag();
            nbt.putString("patchouli:book", "finalevolution:manual");
            book.setTag(nbt);
            player.addItem(book);
        }
    }

    private static boolean hasPlayedBefore(Player player) {
        CompoundTag tag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);

        if (tag.getBoolean(PLAYED_BEFORE)) {
            return true;
        }

        tag.putBoolean(PLAYED_BEFORE, true);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, tag);

        return false;
    }

    public static class ChatMessageObject {

        public String username;
        public String message;
        public String uuid;
        public boolean isHidden;

        public ChatMessageObject(String username, String message, String uuid, boolean isHidden) {
            this.username = username;
            this.message = message;
            this.uuid = uuid;
            this.isHidden = isHidden;
        }
    }


}
