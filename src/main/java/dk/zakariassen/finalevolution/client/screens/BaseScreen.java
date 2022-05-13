package dk.zakariassen.finalevolution.client.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

public class BaseScreen extends Screen {
    protected BaseScreen(Component p_96550_) {
        super(p_96550_);
    }

    @Override
    public boolean keyPressed(int pressedKey, int p_96553_, int p_96554_) {
        // If super has a handler, then use that
        if (super.keyPressed(pressedKey, p_96553_, p_96554_)) {
            return true;
        }

        // If the key pressed is a close screen key, then close the screen
        if (this.getScreenClosingKeys().contains(pressedKey)){
            this.onClose();

            return true;
        }

        // Otherwise, there are no actions
        return false;
    }

    protected List<Integer> getScreenClosingKeys() {
        return Arrays.asList(GLFW.GLFW_KEY_ESCAPE, Minecraft.getInstance().options.keyInventory.getKey().getValue());
    }
}
