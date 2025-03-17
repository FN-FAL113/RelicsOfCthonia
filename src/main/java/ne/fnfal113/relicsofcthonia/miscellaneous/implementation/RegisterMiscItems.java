package ne.fnfal113.relicsofcthonia.miscellaneous.implementation;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import ne.fnfal113.relicsofcthonia.items.RelicItems;
import ne.fnfal113.relicsofcthonia.miscellaneous.relicvoiders.CommonRelicVoider;
import ne.fnfal113.relicsofcthonia.miscellaneous.relicvoiders.EpicRelicVoider;
import ne.fnfal113.relicsofcthonia.miscellaneous.relicvoiders.LegendaryRelicVoider;
import ne.fnfal113.relicsofcthonia.miscellaneous.relicvoiders.RareRelicVoider;
import ne.fnfal113.relicsofcthonia.miscellaneous.relicvoiders.UncommonRelicVoider;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RegisterMiscItems {

    public static void setup(SlimefunAddon instance) {
        new CommonRelicVoider(RelicItems.MISCELLANEOUS, RelicItems.COMMON_RELIC_VOIDER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                new ItemStack(Material.NETHERRACK), null, new ItemStack(Material.NETHERRACK),
                SlimefunItems.MAGIC_LUMP_1.item(), SlimefunItems.MAGICAL_GLASS.item(), SlimefunItems.MAGIC_LUMP_1.item(),
                new ItemStack(Material.NETHERRACK), null, new ItemStack(Material.NETHERRACK)
        }).register(instance);

        new UncommonRelicVoider(RelicItems.MISCELLANEOUS, RelicItems.UNCOMMON_RELIC_VOIDER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                new ItemStack(Material.NETHERRACK), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.NETHERRACK),
                SlimefunItems.MAGIC_LUMP_1.item(), SlimefunItems.MAGICAL_GLASS.item(), SlimefunItems.MAGIC_LUMP_1.item(),
                new ItemStack(Material.NETHERRACK), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.NETHERRACK)
        }).register(instance);

        new RareRelicVoider(RelicItems.MISCELLANEOUS, RelicItems.RARE_RELIC_VOIDER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                new ItemStack(Material.END_STONE), new ItemStack(Material.BONE), new ItemStack(Material.END_STONE),
                SlimefunItems.MAGIC_LUMP_2.item(), SlimefunItems.MAGICAL_GLASS.item(), SlimefunItems.MAGIC_LUMP_2.item(),
                new ItemStack(Material.NETHERRACK), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.NETHERRACK)
        }).register(instance);

        new EpicRelicVoider(RelicItems.MISCELLANEOUS, RelicItems.EPIC_RELIC_VOIDER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.ROTTEN_FLESH),
                SlimefunItems.MAGIC_LUMP_2.item(), SlimefunItems.MAGICAL_GLASS.item(), SlimefunItems.MAGIC_LUMP_2.item(),
                new ItemStack(Material.NETHERRACK), new ItemStack(Material.BONE), new ItemStack(Material.NETHERRACK)
        }).register(instance);

        new LegendaryRelicVoider(RelicItems.MISCELLANEOUS, RelicItems.LEGENDARY_RELIC_VOIDER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                new ItemStack(Material.BLAZE_POWDER), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.BLAZE_POWDER),
                SlimefunItems.MAGIC_LUMP_3.item(), SlimefunItems.MAGICAL_GLASS.item(), SlimefunItems.MAGIC_LUMP_3.item(),
                new ItemStack(Material.SOUL_SAND), new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.NETHERRACK)
        }).register(instance);
    }

}
