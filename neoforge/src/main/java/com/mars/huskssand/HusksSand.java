package com.mars.huskssand;


import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.mars.huskssand.Constants.MOD_ID;

@Mod(MOD_ID)
public class HusksSand {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public static final Supplier<MapCodec<HuskLootModifier>> MY_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("husklootmodifier", () -> HuskLootModifier.CODEC);
    public HusksSand(IEventBus eventBus) {
        CommonClass.init();
        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
