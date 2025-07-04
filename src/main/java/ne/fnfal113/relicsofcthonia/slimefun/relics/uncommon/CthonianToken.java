package ne.fnfal113.relicsofcthonia.slimefun.relics.uncommon;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import ne.fnfal113.relicsofcthonia.api.OffHandRightClickHandler;
import ne.fnfal113.relicsofcthonia.api.Rarity;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import ne.fnfal113.relicsofcthonia.utils.Utils;

import javax.annotation.ParametersAreNonnullByDefault;

public class CthonianToken extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public CthonianToken(ItemGroup itemGroup, SlimefunItemStack item, double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, Rarity.UNCOMMON, dropChance, piglinRewardAmount, defaultDropSize);
        addItemHandler((OffHandRightClickHandler) (event, player, offHand) ->
                Utils.sendRelicMessage("&eThis might be worth trading off to piglins since I have no uses for it.", player));
    }

}
