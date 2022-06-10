package ne.fnfal113.relicsofcthonia.relics.items.legendary;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.relicsofcthonia.relics.abstracts.AbstractRelic;
import ne.fnfal113.relicsofcthonia.relics.implementation.Rarity;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;

public class SkullOfPrometheus extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public SkullOfPrometheus(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                             double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, recipeType, recipe, dropChance, piglinRewardAmount, defaultDropSize);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent event, Player player, ItemStack itemInOffhand) {
        if(!player.hasPotionEffect(PotionEffectType.CONDUIT_POWER)){
            consumeRelic(itemInOffhand);
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 500, 1));

            Utils.sendRelicMessage("&eThis skull is a powerful one, it gave me conduit power for trying to wear it but it got destroyed.", player);
        }
    }
}
