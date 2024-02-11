package ne.fnfal113.relicsofcthonia.utils;

import ne.fnfal113.relicsofcthonia.RelicsOfCthonia;
import ne.fnfal113.relicsofcthonia.config.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Utils {

    public static String colorTranslator(String strings){
        return ChatColor.translateAlternateColorCodes('&', strings);
    }

    public static void sendRelicMessage(String messageKey, LivingEntity livingEntity){
        String prefix = RelicsOfCthonia.getInstance().getLocale().string("prefix");
        String m = RelicsOfCthonia.getInstance().getLocale().string("messages."+messageKey);
        livingEntity.sendMessage(colorTranslator( prefix+m));
    }

    public static void sendRelicMessage(String messageKey, LivingEntity livingEntity, Map<String, String> replace){
        String prefix = RelicsOfCthonia.getInstance().getLocale().string("prefix");
        String m = RelicsOfCthonia.getInstance().getLocale().string("messages."+messageKey);
        for(Map.Entry<String, String> entry : replace.entrySet()){
            m = m.replace(entry.getKey(), entry.getValue());
        }
        livingEntity.sendMessage(colorTranslator( prefix+m));
    }



    // set or update the given string to replace with the given config section
    // and settings that returns an integer value as the new string.


    public static void setLoreByConfigValue(ItemStack itemStack, String section, String settings, String stringToReplace, String color, String suffix, String fileName){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        for(int i = 0 ; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + stringToReplace))){
                Object value = RelicsOfCthonia.getInstance().getConfigManager().getCustomConfig(fileName).get(section + "." + settings);
                if(!value.equals(0) && !value.equals(0.0)) {
                    String line = lore.get(i).replace(Utils.colorTranslator(color + stringToReplace),
                            Utils.colorTranslator(color + value + suffix));

                    lore.set(i, line);
                }
            }
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public static void setLoreByFixedIntValue(ItemStack itemStack, int value, String stringToReplace, String color, String suffix){
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        for(int i = 0 ; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + stringToReplace))){
                if(value != 0) {
                    String line = lore.get(i).replace(Utils.colorTranslator(color + stringToReplace),
                            Utils.colorTranslator(color + value + suffix));

                    lore.set(i, line);
                }
            }
        }
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    // loops the provided list of string and appends them on the designated index
    // based on the string that will be replaced, but instead it is used as the index
    public static void setLoreByConfigStringList(ItemStack itemStack, String section, String settings, String stringToReplace, String color, String prefix, String suffix, String configFileName){
        ConfigManager configManager = RelicsOfCthonia.getInstance().getConfigManager();
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        int j = 0;

        for(int i = 0 ; i < lore.size(); i++){
            if(lore.get(i).contains(Utils.colorTranslator(color + stringToReplace))){
                j = i;
                break;
            }
        }

        for (int x = 0; x < configManager.getCustomConfig(configFileName).getStringList(section + "." + settings).size(); x++) {
            String value = configManager.getCustomConfig("relic-settings").getStringList(section + "." + settings).get(x);

            if(!value.isEmpty()) {
                lore.add(j + 1, Utils.colorTranslator(color + prefix + value.replace("_", " ").toLowerCase()));
            }
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public static void createDelayedTask(Consumer<BukkitTask> taskConsumer, long delay){
        Bukkit.getScheduler().runTaskLater(RelicsOfCthonia.getInstance(), taskConsumer, delay);
    }

    public static void createAsyncTask(Consumer<BukkitTask> taskConsumer){
        Bukkit.getScheduler().runTaskAsynchronously(RelicsOfCthonia.getInstance(), taskConsumer);
    }

    public static void createSyncTask(Consumer<BukkitTask> taskConsumer){
        Bukkit.getScheduler().runTask(RelicsOfCthonia.getInstance(), taskConsumer);
    }

    public static NamespacedKey createKey(String id){
        return new NamespacedKey(RelicsOfCthonia.getInstance(), id.toLowerCase());
    }

    public static <T> void logger(String prefix, T obj){
        RelicsOfCthonia.getInstance().getLogger().info(prefix+ ": " + obj.toString());
    }

}
