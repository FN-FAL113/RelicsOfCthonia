package ne.fnfal113.relicsofcthonia.utils;

import io.github.bakedlibs.dough.common.ChatColors;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;

import static ne.fnfal113.relicsofcthonia.RelicsOfCthonia.CONFIG_MANAGER;

public class Utils {

    public static void sendRelicMessage(String message, LivingEntity entity) {
        entity.sendMessage(ChatColors.color("&6[Relics] > " + message));
    }

    public static void replaceLoreValue(ItemStack itemStack, String replace, String color, String prefix, String suffix, Object value) {
        replaceLoreValue(itemStack, replace, color, prefix, suffix, value, false);
    }

    public static void replaceLoreValue(ItemStack itemStack, String replace, String color, String prefix, String suffix, Object value, boolean wholeLine) {
        if (value == null || value.equals(0) || value.equals(0.0)) {
            return;
        }

        itemStack.editMeta(meta -> {
            List<String> lore = meta.getLore();
            String find = ChatColors.color(color + replace);
            for (int i = 0 ; i < lore.size(); i++){
                String line = lore.get(i);
                if (line.contains(find)){
                    String formatted = ChatColors.color(color + prefix + value + suffix);
                    lore.set(i, wholeLine ? formatted : line.replace(find, formatted));
                }
            }
            meta.setLore(lore);
        });
    }

    public static void replaceLoreList(ItemStack itemStack, String stringToReplace, String color, String prefix, List<String> values) {
        if (values.isEmpty()) {
            return;
        }

        itemStack.editMeta(meta -> {
            List<String> lore = meta.getLore();

            int index = 0;
            String find = ChatColors.color(color + stringToReplace);
            for(int i = 0 ; i < lore.size(); i++){
                if (lore.get(i).contains(find)){
                    index = i;
                    break;
                }
            }

            for (String value : values) {
                if(!value.isEmpty()) {
                    lore.add(index + 1, ChatColors.color(color + prefix + value));
                }
            }

            meta.setLore(lore);
        });
    }

    public static String translate(String key) {
        return new TranslatableComponent(key).toPlainText();
    }

    public static void setField(Class<?> clazz, String fieldName, Object instance, Object value) {
        Field field;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            field.set(instance, value);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getField(Class<?> clazz, String fieldName, Object instance) {
        Field field;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            return field.get(instance);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();

            return null;
        }
    }
}
