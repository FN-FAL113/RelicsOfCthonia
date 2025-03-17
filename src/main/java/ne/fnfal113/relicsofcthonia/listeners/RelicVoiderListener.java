package ne.fnfal113.relicsofcthonia.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import ne.fnfal113.relicsofcthonia.miscellaneous.abstracts.AbstractRelicVoider;
import ne.fnfal113.relicsofcthonia.relics.abstracts.AbstractRelic;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;
import java.util.function.Predicate;

public class RelicVoiderListener implements Listener {

    @EventHandler
    public void onChestMenuClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        InventoryView inventoryView = event.getView();

        Player player = (Player) event.getWhoClicked();

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemStack.getItemMeta();

        if (! meta.hasDisplayName()) {
            return;
        }

        if (! inventoryView.getTitle().equals(Utils.colorTranslator(meta.getDisplayName()))) {
            return;
        }

        Optional<SlimefunItem> slimefunItem = Optional.ofNullable(SlimefunItem.getByItem(itemStack));

        slimefunItem.ifPresent(sfItem -> {
            if (sfItem instanceof AbstractRelicVoider) {
                event.setCancelled(true);

                ((AbstractRelicVoider) sfItem).onInventoryClick(event, itemStack);
            }
        });
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if ((event.getEntity() instanceof Player)) {
            return;
        }

        Item pickedUpItem = event.getItem();
        ItemStack pickedUpItemStack = pickedUpItem.getItemStack();

        Optional<SlimefunItem> pickedUpSfItem = Optional.ofNullable(SlimefunItem.getByItem(pickedUpItemStack));

        pickedUpSfItem.ifPresent((pickedUpRelic) -> {
            if (! (pickedUpRelic instanceof AbstractRelic)) {
                return;
            }

            Player player = (Player) event.getEntity();
            Inventory inv = player.getInventory();

            AbstractRelic relic = (AbstractRelic) pickedUpRelic;

            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
                    continue;
                }

                ItemStack voiderItemStack = inv.getItem(i);
                Optional<SlimefunItem> voiderSfItemstack = Optional.ofNullable(SlimefunItem.getByItem(voiderItemStack));

                if (! voiderSfItemstack.isPresent() || ! (voiderSfItemstack.get() instanceof AbstractRelicVoider)) {
                    continue;
                }

                AbstractRelicVoider relicVoider = (AbstractRelicVoider) voiderSfItemstack.get();

                if (relic.getRarity() == relicVoider.getRarity()) {
                    int pickedUpRelicCondition = relic.getRelicCondition(pickedUpItemStack);

                    relicVoider.onRelicPickup(event, voiderItemStack, pickedUpItem, pickedUpRelicCondition);

                    break;
                }
            }
        });

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Predicate<Action> isRightClick = action -> action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR;

        if (isRightClick.test(event.getAction())) {
            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

            if (itemStack.getType() == Material.AIR) {
                return;
            }

            Optional<SlimefunItem> slimefunItem = Optional.ofNullable(SlimefunItem.getByItem(itemStack));

            slimefunItem.ifPresent(sfItem -> {
                if (sfItem instanceof AbstractRelicVoider) {
                    ((AbstractRelicVoider) sfItem).onClick(itemStack, event.getPlayer());
                    
                    event.setCancelled(true);
                }
            });
        }
    }

}
