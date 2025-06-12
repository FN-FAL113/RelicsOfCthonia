package ne.fnfal113.relicsofcthonia.slimefun.relics.uncommon;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import ne.fnfal113.relicsofcthonia.api.OffHandRightClickHandler;
import ne.fnfal113.relicsofcthonia.api.Rarity;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.ParametersAreNonnullByDefault;

public class GrayBerry extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public GrayBerry(ItemGroup itemGroup, SlimefunItemStack item, double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, Rarity.UNCOMMON, dropChance, piglinRewardAmount, defaultDropSize);
        addItemHandler((OffHandRightClickHandler) (event, player, offHand) -> {
            if(!player.hasPotionEffect(PotionEffectType.BLINDNESS)){
                offHand.subtract();
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                Utils.sendRelicMessage("&eOh this berry is something I would not eat again! I rather trade this to piglins.", player);
            }
        });
    }

}
