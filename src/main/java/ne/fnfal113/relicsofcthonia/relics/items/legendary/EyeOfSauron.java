package ne.fnfal113.relicsofcthonia.relics.items.legendary;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import ne.fnfal113.relicsofcthonia.relics.abstracts.AbstractRelic;
import ne.fnfal113.relicsofcthonia.relics.implementation.Rarity;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EyeOfSauron extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public EyeOfSauron(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                        double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, recipeType, recipe, dropChance, piglinRewardAmount, defaultDropSize);
    }

    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent event, Player player, ItemStack itemInOffhand) {
        List<Player> playerList = new ArrayList<>();

        for (Entity en : player.getNearbyEntities(100, 60, 100)) {
            if(en instanceof Player){
                int x = (int) player.getLocation().getX();
                int y = (int) player.getLocation().getY();
                int z = (int) player.getLocation().getZ();

                playerList.add((Player) en);
                Utils.sendRelicMessage("eye-of-sauron-found", player, Map.of(
                        "%player%", ((Player) en).getDisplayName(),
                        "%x%", x+"",
                        "%y%", y+"",
                        "%z%", z+""
                ));
            }
        }

        if(playerList.isEmpty()){
            Utils.sendRelicMessage("eye-of-sauron-didnt-find", player);
        }

        consumeRelic(itemInOffhand);
    }
}