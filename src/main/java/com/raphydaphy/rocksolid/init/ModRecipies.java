package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.util.RockSolidAPI;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.SeparatorRecipe;
import de.ellpeck.rockbottom.api.construction.SmelterRecipe;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModRecipies 
{
	public static void init()
	{
		// Alloy recipies
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotBronze, 4), new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3), new ItemInstance(ModItems.ingotTin, 1), 500));
		
		// Cluster to grit (separator)
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritTin, 2), new ItemInstance(ModItems.clusterTin), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritIron, 2), new ItemInstance(ModItems.clusterIron), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		
		// grit to ingot (smelter)
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotTin), new ItemInstance(ModItems.gritTin), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotIron), new ItemInstance(ModItems.gritIron), 300));
		
		// Remove super pickaxe recipe
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.remove(6);
		
		// Manual crafting recipies
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.alloySmelter), new ItemInstance[]{new ItemInstance(ModItems.ingotTin, 10),new ItemInstance(GameContent.ITEM_COPPER_INGOT, 10),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 20),new ItemInstance(GameContent.ITEM_ROCK_PICK, 30)}));
		
		// Pickaxe manual crafting
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeTin), new ItemInstance[]{new ItemInstance(ModItems.ingotTin, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeBronze), new ItemInstance[]{new ItemInstance(ModItems.ingotBronze, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeCopper), new ItemInstance[]{new ItemInstance(GameContent.ITEM_COPPER_INGOT, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeIron), new ItemInstance[]{new ItemInstance(ModItems.ingotIron, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
	}
}
