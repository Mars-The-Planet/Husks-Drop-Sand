package com.mars.huskssand;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class HuskLootModifier extends LootModifier {
    private static final Item sandDrop = Blocks.SAND.asItem();
    public static final MapCodec<HuskLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).apply(inst, HuskLootModifier::new));

    public HuskLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(LootTable lootTable, ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(lootContext)) {
                return generatedLoot;
            }
        }

        RandomSource random = lootContext.getRandom();
        int rolls = random.nextIntBetweenInclusive(HusksSandConfig.min_rolls, HusksSandConfig.max_rolls);
        Player player = lootContext.getOptionalParameter(LootContextParams.LAST_DAMAGE_PLAYER);

        if(player != null) {
            ItemStack weapon = player.getMainHandItem();
            int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(
                    lootContext.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING), weapon);

            if (lootingLevel > 0)
                rolls = rolls + random.nextIntBetweenInclusive(0, lootingLevel);
        }

        generatedLoot.add(new ItemStack(sandDrop, rolls));

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
