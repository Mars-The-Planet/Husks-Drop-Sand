package com.mars.huskssand;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class HusksSand implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.equals(EntityType.HUSK.getDefaultLootTable())) {
                LootPool poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.SAND)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(HusksSandConfig.min_rolls, HusksSandConfig.max_rolls)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0, 1))))
                        .build();

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
