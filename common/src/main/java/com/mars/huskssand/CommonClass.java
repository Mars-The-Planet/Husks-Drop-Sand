package com.mars.huskssand;

import com.mars.deimos.config.DeimosConfig;

import static com.mars.huskssand.Constants.MOD_ID;

public class CommonClass {
    public static void init() {
        DeimosConfig.init(MOD_ID, HusksSandConfig.class);
    }
}
