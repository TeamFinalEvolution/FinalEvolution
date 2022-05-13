package dk.zakariassen.finalevolution.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RecipesProvider extends RecipeProvider implements IConditionBuilder {

    public RecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // Reference code
//        ShapedRecipeBuilder.shaped(Blocks.CHAT_BOX.get())
//                .define('P', ItemTags.LOGS)
//                .define('A', ItemTags.LOGS)
//                .define('G', Tags.Items.INGOTS_GOLD)
//                .pattern("PPP")
//                .pattern("PAP")
//                .pattern("PGP")
//                .save(consumer);

        // Reference code
//        ShapelessRecipeBuilder.shapeless(de.srendi.finalevolution.common.setup.Items.OVERPOWERED_HUSBANDRY_AUTOMATA_CORE.get())
//                .requires(de.srendi.finalevolution.common.setup.Items.HUSBANDRY_AUTOMATA_CORE.get())
//                .requires(Items.NETHER_STAR)
//                .unlockedBy("has_item", has(de.srendi.finalevolution.common.setup.Items.HUSBANDRY_AUTOMATA_CORE.get()))
//                .save(consumer);
    }
}
