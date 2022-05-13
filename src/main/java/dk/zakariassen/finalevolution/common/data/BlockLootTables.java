package dk.zakariassen.finalevolution.common.data;

import dk.zakariassen.finalevolution.common.setup.Registration;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockLootTables extends net.minecraft.data.loot.BlockLoot {

    @Override
    protected void addTables() {
        Registration.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
