package ne.fnfal113.relicsofcthonia;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;
import ne.fnfal113.relicsofcthonia.config.ConfigManager;
import ne.fnfal113.relicsofcthonia.items.RelicsItemSetup;
import ne.fnfal113.relicsofcthonia.listeners.MiningListener;
import ne.fnfal113.relicsofcthonia.listeners.MobKillListener;
import ne.fnfal113.relicsofcthonia.listeners.OffHandClickListener;
import ne.fnfal113.relicsofcthonia.listeners.PiglinMainListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.logging.Level;

public final class RelicsOfCthonia extends JavaPlugin implements SlimefunAddon {

    private static RelicsOfCthonia instance;

    private final ConfigManager configManager = new ConfigManager();

    private final RelicsRegistry relicsRegistry = new RelicsRegistry();

    @Override
    public void onEnable() {
        setInstance(this);

        new Metrics(this, 15420);

        getLogger().info("************************************************************");
        getLogger().info("*         Relics of Cthonia - Created by FN_FAL113         *");
        getLogger().info("*               Slimefun Addon Jam 2022 Entry              *");
        getLogger().info("*                     The Nether Theme                     *");
        getLogger().info("************************************************************");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        RelicsItemSetup.INSTANCE.init();

        registerEvents();

        if (getConfig().getBoolean("auto-update", true) && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "FN-FAL113/RelicsOfCthonia/main").start();
        }

    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new MiningListener(), this);
        getServer().getPluginManager().registerEvents(new MobKillListener(), this);
        getServer().getPluginManager().registerEvents(new PiglinMainListener(), this);
        getServer().getPluginManager().registerEvents(new OffHandClickListener(), this);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(RelicsOfCthonia.getInstance());
        getLogger().log(Level.INFO, "Successfully disabled any running task that exist");
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return null;
    }

    private static void setInstance(RelicsOfCthonia ins) {
        instance = ins;
    }

    public static RelicsOfCthonia getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager(){
        return instance.configManager;
    }

    public RelicsRegistry getRelicsRegistry(){
        return instance.relicsRegistry;
    }
}
