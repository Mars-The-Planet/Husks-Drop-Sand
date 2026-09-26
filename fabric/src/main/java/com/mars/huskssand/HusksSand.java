package com.mars.huskssand;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;

public class HusksSand implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            ConstantValue one = new ConstantValue(1);

            if (key.equals(EntityTypes.HUSK.getDefaultLootTable().get())) {
                LootPool poolBuilder = LootPool.lootPool()
                        .setRolls(Holder.direct(new ConstantValue(1)))
                        .add(LootItem.lootTableItem(Items.SAND)
                                .apply(SetItemCountFunction.setCount(Holder.direct(new UniformGenerator(Holder.direct(new ConstantValue(HusksSandConfig.min_rolls)), Holder.direct(new ConstantValue(HusksSandConfig.max_rolls))))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries.lookupOrThrow(Registries.ENCHANTMENT), ContextFloatProviders.between(0.0f, 1.0f))))
                        .build();

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
