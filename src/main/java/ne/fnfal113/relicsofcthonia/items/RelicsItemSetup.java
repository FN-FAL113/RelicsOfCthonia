package ne.fnfal113.relicsofcthonia.items;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import ne.fnfal113.relicsofcthonia.RelicsOfCthonia;
import ne.fnfal113.relicsofcthonia.miscellaneous.implementation.RegisterMiscItems;
import ne.fnfal113.relicsofcthonia.relics.implementation.RegisterRelics;

public class RelicsItemSetup {

    public static final RelicsItemSetup INSTANCE = new RelicsItemSetup();
    private final SlimefunAddon plugin = RelicsOfCthonia.getInstance();
    private boolean initialised;

    public void init() {
        if (initialised) {
            return;
        }

        initialised = true;

        RegisterRelics.registerRelics(plugin);
        RegisterMiscItems.setup(plugin);
    }

}
