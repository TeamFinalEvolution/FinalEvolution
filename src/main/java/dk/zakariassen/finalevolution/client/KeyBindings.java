package dk.zakariassen.finalevolution.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyMapping DESCRIPTION_KEYBINDING = new KeyMapping("keybind.finalevolution.description", GLFW.GLFW_KEY_LEFT_CONTROL, "keybind.finalevolution.category");

    public static void register() {
        net.minecraftforge.client.ClientRegistry.registerKeyBinding(DESCRIPTION_KEYBINDING);
    }

}
