package dk.zakariassen.finalevolution.common.util;

import dk.zakariassen.finalevolution.FinalEvolution;
import net.minecraft.network.chat.TranslatableComponent;

public class TranslationUtil {

    public static TranslatableComponent itemTooltip(String descriptionId) {
        int lastIndex = descriptionId.lastIndexOf('.');
        return new TranslatableComponent(String.format(
                "%s.tooltip.%s",
                descriptionId.substring(0, lastIndex).replaceFirst("^block", "item"),
                descriptionId.substring(lastIndex + 1)
        ));
    }

    public static String turtle(String name) {
        return String.format("turtle.%s.%s", FinalEvolution.MOD_ID, name);
    }

    public static String pocket(String name) {
        return String.format("pocket.%s.%s", FinalEvolution.MOD_ID, name);
    }
}
