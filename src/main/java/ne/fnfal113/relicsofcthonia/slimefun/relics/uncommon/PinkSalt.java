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

public class PinkSalt extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public PinkSalt(ItemGroup itemGroup, SlimefunItemStack item, double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, Rarity.UNCOMMON, dropChance, piglinRewardAmount, defaultDropSize);
        addItemHandler((OffHandRightClickHandler) (event, player, offHand) -> {
            if(!player.hasPotionEffect(PotionEffectType.SLOW_FALLING)){
                offHand.subtract();
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 120, 1));
                Utils.sendRelicMessage("&eAfter consuming it, you get slow falling effect for no reason!", player);
            }
        });
    }
}