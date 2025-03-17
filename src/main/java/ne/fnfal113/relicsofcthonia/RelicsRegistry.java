package ne.fnfal113.relicsofcthonia;

import ne.fnfal113.relicsofcthonia.relics.abstracts.AbstractRelic;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelicsRegistry {

    private final Map<AbstractRelic, List<Material>> whereToDropMaterialMap = new HashMap<>();

    private final Map<AbstractRelic, List<String>> whereToDropMobMap = new HashMap<>();

    private final Map<AbstractRelic, List<String>> piglinRewardList = new HashMap<>();

    public Map<AbstractRelic, List<Material>> getWhereToDropMaterialMap() {
        return whereToDropMaterialMap;
    }

    public Map<AbstractRelic, List<String>> getWhereToDropMobMap() {
        return whereToDropMobMap;
    }

    public Map<AbstractRelic, List<String>> getPiglinRewardList() {
        return piglinRewardList;
    }
    
}
