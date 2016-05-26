package com.fyxridd.lib.potions;

import com.fyxridd.lib.core.api.plugin.SimplePlugin;
import com.fyxridd.lib.potions.manager.PotionsManager;

public class PotionsPlugin extends SimplePlugin{
    public static PotionsPlugin instance;
    
    private PotionsManager potionsManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        potionsManager = new PotionsManager();
        
        super.onEnable();
    }

    public PotionsManager getPotionsManager() {
        return potionsManager;
    }
}