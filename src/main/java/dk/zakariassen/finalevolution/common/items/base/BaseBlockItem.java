package dk.zakariassen.finalevolution.common.items.base;

import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.client.KeyBindings;
import dk.zakariassen.finalevolution.common.util.EnumColor;
import dk.zakariassen.finalevolution.common.util.TranslationUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseBlockItem extends BlockItem {
    private Component description;

    public BaseBlockItem(Block blockIn, Properties properties) {
        super(blockIn, properties.tab(FinalEvolution.TAB));
    }

    public BaseBlockItem(Block blockIn) {
        super(blockIn, new Properties().tab(FinalEvolution.TAB));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level levelIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, levelIn, tooltip, flagIn);

        if (!KeyBindings.DESCRIPTION_KEYBINDING.isDown()) {
            tooltip.add(EnumColor.buildTextComponent(new TranslatableComponent("item.finalevolution.tooltip.show_desc", KeyBindings.DESCRIPTION_KEYBINDING.getTranslatedKeyMessage())));
        } else {
            tooltip.add(EnumColor.buildTextComponent(getDescription()));
        }

        if (!isEnabled())
            tooltip.add(EnumColor.buildTextComponent(new TranslatableComponent("item.finalevolution.tooltip.disabled")));
    }

    public @NotNull Component getDescription() {
        if (description == null)
            description = TranslationUtil.itemTooltip(this.getDescriptionId());
        return description;
    }

    public abstract boolean isEnabled();
}
