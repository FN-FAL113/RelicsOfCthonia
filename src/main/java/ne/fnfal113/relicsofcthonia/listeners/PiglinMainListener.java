package ne.fnfal113.relicsofcthonia.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.items.misc.StrangeNetherGoo;
import ne.fnfal113.relicsofcthonia.RelicsOfCthonia;
import ne.fnfal113.relicsofcthonia.RelicsRegistry;
import ne.fnfal113.relicsofcthonia.core.ItemStacks;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static ne.fnfal113.relicsofcthonia.core.Keys.CURRENTLY_TRADING_PLAYER;

public class PiglinMainListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPiglinRightClick(PlayerInteractEntityEvent event){
        if (!(event.getRightClicked() instanceof Piglin piglin)) {
            return;
        }

        if (isCurrentlyTradingRelic(piglin)) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        if (event.getHand() == EquipmentSlot.OFF_HAND
                || !piglin.isAdult()
                || piglin.hasMetadata("NPC")
                || !(SlimefunItem.getByItem(mainHandItem) instanceof AbstractRelic)
        ) {
            return;
        }

        // Heads cannot be traded by default - in order to allow relics to be traded we have to add relics as an
        // allowed barter material
        piglin.addBarterMaterial(mainHandItem.getType());

        setCurrentlyTradingPlayer(piglin, player);

        // Start trade - needs to be run later because the trading starts after this event has been processed (I think)
        // TODO re add the animation, it has to be delayed otherwise the piglin IMMEDIATELY does the barter & gives it
        Bukkit.getScheduler().runTask(RelicsOfCthonia.getInstance(), () -> {
            piglin.getEquipment().setItemInOffHand(mainHandItem);
            mainHandItem.setAmount(mainHandItem.getAmount() - 1);
        });

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(EntityDropItemEvent event){
        if (!(event.getEntity() instanceof Piglin piglin) || !isCurrentlyTradingRelic(piglin)) {
            return;
        }

        // TODO check if this is needed?
//        piglin.removeMetadata(CURRENTLY_TRADING_PLAYER, RelicsOfCthonia.getInstance());

        // slimefun might override the output to be nether goo even if a relic trade is in progress
        if (SlimefunItem.getByItem(event.getItemDrop().getItemStack()) instanceof StrangeNetherGoo && !event.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBarter(PiglinBarterEvent event){
        Piglin piglin = event.getEntity();
        UUID uuid = getCurrentlyTradingPlayer(piglin);
        if (uuid == null) {
            return;
        }

        Optional<Player> player = Optional.ofNullable(Bukkit.getPlayer(uuid));

        // can happen if a plugin cancels the event for some reason
        if (event.isCancelled()) {
            clearCurrentlyTradingPlayer(piglin);
            player.ifPresent(p -> p.sendMessage("&cFailed to trade!"));
            return;
        }

        // item should always be an abstract relic because the piglin has the CURRENTLY_TRADING_RELIC metadata
        SlimefunItem relicItem = SlimefunItem.getByItem(event.getInput());
        if (!(relicItem instanceof AbstractRelic relic)) {
            clearCurrentlyTradingPlayer(piglin);
            RelicsOfCthonia.getInstance().getLogger().severe("Something modified the piglin's input item while a RelicsOfCthonia trade was in progress");
            player.ifPresent(p -> p.sendMessage("&cAn internal error occurred during the trade"));
            return;
        }

        // random chance for trade to succeed
        if (ThreadLocalRandom.current().nextInt(100) > AbstractRelic.getRelicCondition(event.getInput())) {
            // TODO: Notify failed trade
            clearCurrentlyTradingPlayer(piglin);
            return;
        }

        // get relic's corresponding rewards
        List<ItemStack> rewards = RelicsRegistry.RELIC_OUTPUTS.get(relic);
        if (rewards == null || rewards.isEmpty()) {
            clearCurrentlyTradingPlayer(piglin);
            RelicsOfCthonia.getInstance().getLogger().severe("&cThe relic " + relic.getId() + " does not have a corresponding reward list");
            player.ifPresent(p -> p.sendMessage("&cAn internal error occurred during the trade"));
            return;
        }

        // remove item that's being traded (TODO is this really necessary?)
        clearCurrentlyTradingPlayer(piglin);

        // set reward
        event.getOutcome().clear();
        int rewardIndex = ThreadLocalRandom.current().nextInt(0, rewards.size());
        ItemStack reward = rewards.get(rewardIndex).clone();
        reward.setAmount(relic.getPiglinRewardAmount());
        event.getOutcome().add(reward);

        // particles
        piglin.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, piglin.getLocation().add(0, 2.2, 0), 0);
        piglin.getWorld().playSound(piglin.getLocation(), Sound.ENTITY_PIGLIN_ADMIRING_ITEM, 1.0F, 1.0F);
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Piglin piglin)) {
            return;
        }

        if (isCurrentlyTradingRelic(piglin)) {
            // TODO why is this needed
//            e.getDrops().clear();
//            piglin.removeMetadata(CURRENTLY_TRADING_PLAYER, RelicsOfCthonia.getInstance());
        }
    }

    private void setCurrentlyTradingPlayer(Piglin piglin, Player player) {
        piglin.getPersistentDataContainer().set(CURRENTLY_TRADING_PLAYER, PersistentDataType.STRING, player.getUniqueId().toString());
    }

    private void clearCurrentlyTradingPlayer(Piglin piglin) {
        piglin.getPersistentDataContainer().remove(CURRENTLY_TRADING_PLAYER);
    }

    private UUID getCurrentlyTradingPlayer(Piglin piglin) {
        String idString = piglin.getPersistentDataContainer().get(CURRENTLY_TRADING_PLAYER, PersistentDataType.STRING);
        return idString == null
                ? null
                : UUID.fromString(idString);
    }

    private boolean isCurrentlyTradingRelic(Piglin piglin) {
        return getCurrentlyTradingPlayer(piglin) != null;
    }
}