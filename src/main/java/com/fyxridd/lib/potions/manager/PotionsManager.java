package com.fyxridd.lib.potions.manager;

import com.fyxridd.lib.core.api.CoreApi;
import com.fyxridd.lib.core.api.UtilApi;
import com.fyxridd.lib.potions.api.PotionsApi;
import com.fyxridd.lib.potions.model.Potion;
import com.fyxridd.lib.potions.model.Potion.PotionItem;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.util.*;

public class PotionsManager{
    //插件名 类型名 类型信息
    private static  Map<String, Map<String, Potion>> potions = new HashMap<>();

	/**
	 * @see PotionsApi#reloadPotions(String)
	 */
    public void reloadPotions(String plugin) {
        try {
            try {
                if (plugin == null) return;
                YamlConfiguration config = UtilApi.loadConfigByUTF8(new File(CoreApi.pluginPath, plugin+File.separator+"potions.yml"));
                if (config == null) return;
                Map<String, Potion> map = new HashMap<String, Potion>();
                potions.put(plugin, map);
                for (String key:config.getValues(false).keySet()) {
                    //mode
                    int mode = config.getInt(key+".mode");
                    if (mode < 1 || mode > 3) throw new Exception("error: mode < 1 or mode > 3");
                    //all
                    boolean all = config.getBoolean(key+".all");
                    //potions
                    List<PotionItem> potions = new ArrayList<PotionItem>();
                    for (String s : config.getStringList(key + ".potions")) {
                        PotionItem potionItem = PotionItem.load(s);
                        if (potionItem == null) throw new Exception("potion load error!");
                        potions.add(potionItem);
                    }
                    //添加
                    map.put(key, new Potion(mode, all, potions));
                }
            } catch (Exception e) {
                throw new Exception("load potion of plugin '"+plugin+"' error: ", e);
            }
        } catch (Exception e) {
            //todo
        }
    }

    /**
     * @see com.fyxridd.lib.potions.api.PotionsApi#addPotions(String, String, org.bukkit.entity.LivingEntity)
     */
    public List<PotionEffect> addPotions(String plugin, String type, LivingEntity le) {
        if (plugin == null || type == null || le == null) return new ArrayList<PotionEffect>();
        //不存在此注册的药效类型
        if (!potions.containsKey(plugin) || !potions.get(plugin).containsKey(type)) return new ArrayList<PotionEffect>();
        //检测添加
        return potions.get(plugin).get(type).addPotions(le);
    }
}
