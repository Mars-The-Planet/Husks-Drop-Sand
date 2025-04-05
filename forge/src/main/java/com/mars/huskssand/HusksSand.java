package com.mars.huskssand;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mars.huskssand.Constants.MOD_ID;

@Mod(MOD_ID)
public class HusksSand {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> lootModifiers = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, MOD_ID);
    public HusksSand() {
        CommonClass.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        lootModifiers.register(modEventBus);

        lootModifiers.register("husklootmodifier", HuskLootModifier.Serializer::new);
    }
}
