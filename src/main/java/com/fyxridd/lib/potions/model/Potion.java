package com.fyxridd.lib.potions.model;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.fyxridd.lib.core.api.CoreApi;
import com.fyxridd.lib.core.api.getter.MultiRandomInt;

public class Potion {
    public static class PotionItem {
        PotionEffectType potionEffectType;
        MultiRandomInt time;
        MultiRandomInt level;

        PotionItem(PotionEffectType potionEffectType, MultiRandomInt time, MultiRandomInt level) {
            this.potionEffectType = potionEffectType;
            this.time = time;
            this.level = level;
        }

        /**
         * 从字符串中读取PotionItem
         * @param s 定义字符串
         * @return 异常返回null
         */
        public static PotionItem load(String s) {
            String[] ss = s.split(" ");
            if (ss.length != 3) return null;
            PotionEffectType potionEffectType = CoreApi.getPotionEffectType(ss[0]);
            if (potionEffectType == null) return null;
            return new PotionItem(potionEffectType, new MultiRandomInt(ss[1]), new MultiRandomInt(ss[2]));
        }
    }
    
    private int mode;
    private boolean all;
    private List<PotionItem> potions;

    public Potion(int mode, boolean all, List<PotionItem> potions) {
        this.mode = mode;
        this.all = all;
        this.potions = potions;
    }

    public List<PotionEffect> addPotions(LivingEntity le) {
        List<PotionEffect> result = new ArrayList<PotionEffect>();
        if (potions != null) {
            for (PotionItem potionItem:potions) {
                //数据
                int time = potionItem.time.get(0);
                if (time == 0) continue;
                int level = potionItem.level.get(0);
                if (level == -1) continue;

                int preLevel = -1;
                int preTime = 0;
                for (PotionEffect pe:le.getActivePotionEffects()) {
                    if (pe.getType().equals(potionItem.potionEffectType)) {
                        preLevel = pe.getAmplifier();
                        preTime = pe.getDuration();
                    }
                }

                //检测是否添加药效
                switch (mode) {
                    case 1:
                        if (preLevel > -1 && preTime > 0) {
                            if (preLevel > level || preTime >= time) continue;
                        }
                        break;
                    case 3:
                        if (preLevel > -1 && preTime > 0) continue;
                }

                //添加药效
                PotionEffect pe = new PotionEffect(potionItem.potionEffectType, time, level, false);
                le.addPotionEffect(pe, true);
                result.add(pe);

                //是否检测下个药效
                if (!all) break;
            } 
        }
        return result;
    }
}
