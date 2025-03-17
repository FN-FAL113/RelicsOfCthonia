package ne.fnfal113.relicsofcthonia.relics.items.common;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.compatibility.VersionedPotionEffectType;
import ne.fnfal113.relicsofcthonia.relics.abstracts.AbstractRelic;
import ne.fnfal113.relicsofcthonia.relics.implementation.Rarity;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.ParametersAreNonnullByDefault;

public class Mushroom extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public Mushroom(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
        double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, recipeType, recipe, dropChance, piglinRewardAmount, defaultDropSize);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent event, Player player, ItemStack itemInOffhand) {
        if (!player.hasPotionEffect(VersionedPotionEffectType.NAUSEA)) {
            consumeRelic(itemInOffhand);

            player.addPotionEffect(new PotionEffect(VersionedPotionEffectType.NAUSEA, 160, 1));

            Utils.sendRelicMessage("&eMushroom is not edible! but you can trade it instead.", player);
        }
    }

}