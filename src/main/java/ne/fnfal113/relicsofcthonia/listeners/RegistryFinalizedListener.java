package ne.fnfal113.relicsofcthonia.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunItemRegistryFinalizedEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RegistryFinalizedListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onSlimefunItemRegistryFinalized(SlimefunItemRegistryFinalizedEvent event) {
        for (SlimefunItem item : Slimefun.getRegistry().getAllSlimefunItems()) {
            if (item instanceof AbstractRelic relic) {
                relic.postInit();
            }
        }
    }
}
