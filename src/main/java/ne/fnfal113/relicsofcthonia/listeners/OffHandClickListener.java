 package ne.fnfal113.relicsofcthonia.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.relicsofcthonia.relics.abstracts.AbstractRelic;
import ne.fnfal113.relicsofcthonia.relics.implementation.OffHandRightClickHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class OffHandClickListener implements Listener {

    @EventHandler
    public void onOffHandRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (event.getHand() == EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInOffHand();

        if (itemStack.getType() == Material.AIR) {
            return;
        }

        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);

        if (slimefunItem instanceof AbstractRelic) {
            AbstractRelic relic = (AbstractRelic) slimefunItem;

            relic.callRelicHandler(OffHandRightClickHandler.class, relicHandler -> relicHandler.onItemRightClick(event, player, itemStack), relic);
        }
    }

}
