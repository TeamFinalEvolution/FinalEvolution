package dk.zakariassen.finalevolution.common.items.base;

import net.minecraft.world.level.block.Block;

public class BaseEnabledBlockItem extends BaseBlockItem {
    public BaseEnabledBlockItem(Block blockIn) {
        super(blockIn);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
