package com.mars.huskssand;

import com.mars.deimos.config.DeimosConfig;
import com.mars.huskssand.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import static com.mars.huskssand.Constants.MOD_ID;

public class CommonClass {
    public static void init() {
        DeimosConfig.init(MOD_ID, HusksSandConfig.class);
    }
}
