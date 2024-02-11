package ne.fnfal113.relicsofcthonia.config;

import ne.fnfal113.relicsofcthonia.RelicsOfCthonia;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Locale {

    YamlConfiguration configuration;
    File file;

    public Locale(){
        try {
            file = new File(RelicsOfCthonia.getInstance().getDataFolder() + File.separator + "language.yml");
            if (!file.exists()) {
                RelicsOfCthonia.getInstance().saveResource("language.yml", false);
            }
            configuration = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String string(String key){
        if(configuration == null) return key;
        if(!configuration.contains(key)){
            insertKey(key);
            return key;
        }

        return configuration.getString(key);
    }

    public List<String> stringList(String key){
        List<String> result = new ArrayList<>();
        if(configuration == null){
            result.add(key);
            return result;
        }

        if(!configuration.contains(key)){
            insertKey(key);
            result.add(key);
            return result;
        }

        if(configuration.isString(key)){
            result.add(configuration.getString(key));
            return result;
        }

        return configuration.getStringList(key);
    }

    public String[] stringArray(String key){
        List<String> list = stringList(key);
        String[] strings = new String[list.size()];
        for(int i=0;i<list.size();i++){
            strings[i] = list.get(i);
        }
        return strings;
    }

    private void insertKey(String key){
        if(file == null || configuration == null){
            Bukkit.getLogger().severe("language.yml could not be loaded properly!");
            return;
        }
        try {
            configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set(key, key);
            configuration.save(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
