package ne.fnfal113.relicsofcthonia.relics.items.rare;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.relicsofcthonia.relics.abstracts.AbstractRelic;
import ne.fnfal113.relicsofcthonia.relics.implementation.Rarity;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

public class BlazeAshes extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public BlazeAshes(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                    double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, recipeType, recipe, dropChance, piglinRewardAmount, defaultDropSize);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent event, Player player, ItemStack itemInOffhand) {
        double x;
        double y;
        double z;

        for (double i = 0; i < 360; i++) {
            x = Math.cos(i);
            z = Math.sin(i);
            y = i / 180;

            player.getWorld().spawnParticle(Particle.ASH, player.getLocation().clone().add(x, y, z), 0);
        }

        consumeRelic(itemInOffhand);
        Utils.sendRelicMessage("blaze-ashes", player);
    }

}