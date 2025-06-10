package ne.fnfal113.relicsofcthonia.slimefun.relics.epic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import ne.fnfal113.relicsofcthonia.api.OffHandRightClickHandler;
import ne.fnfal113.relicsofcthonia.api.Rarity;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import ne.fnfal113.relicsofcthonia.utils.Utils;

import javax.annotation.ParametersAreNonnullByDefault;

public class FervorHelmet extends AbstractRelic {

    @ParametersAreNonnullByDefault
    public FervorHelmet(ItemGroup itemGroup, SlimefunItemStack item, double dropChance, int piglinRewardAmount, int defaultDropSize) {
        super(itemGroup, item, Rarity.EPIC, dropChance, piglinRewardAmount, defaultDropSize);
        addItemHandler((OffHandRightClickHandler) (event, player, offHand) ->
                Utils.sendRelicMessage("&eFervor helmet cannot be used anymore, trade it instead!", player));
    }

}