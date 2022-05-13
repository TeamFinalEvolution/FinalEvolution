package dk.zakariassen.finalevolution.common.items;

import dk.zakariassen.finalevolution.common.items.base.BaseItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class APItem extends BaseItem {
    private final Supplier<Boolean> enabledSup;

    public APItem(Properties properties, @Nullable ResourceLocation turtleID, @Nullable ResourceLocation pocketID, Supplier<Boolean> enabledSup) {
        super(properties);
        this.enabledSup = enabledSup;
    }

    public APItem(@Nullable ResourceLocation turtleID, @Nullable ResourceLocation pocketID, Supplier<Boolean> enabledSup) {
        super();
        this.enabledSup = enabledSup;
    }

    @Override
    public boolean isEnabled() {
        return enabledSup.get();
    }

}
