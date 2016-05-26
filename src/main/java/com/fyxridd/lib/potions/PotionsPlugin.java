package com.fyxridd.lib.potions;

import com.fyxridd.lib.core.api.config.ConfigApi;
import com.fyxridd.lib.core.api.plugin.SimplePlugin;
import com.fyxridd.lib.msg.config.LangConfig;
import com.fyxridd.lib.msg.config.MsgConfig;
import com.fyxridd.lib.msg.config.ScoreboardConfig;
import com.fyxridd.lib.msg.manager.InfoManager;
import com.fyxridd.lib.msg.manager.MsgManager;
import com.fyxridd.lib.msg.manager.OuterManager;
import com.fyxridd.lib.msg.manager.ScoreboardManager;
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