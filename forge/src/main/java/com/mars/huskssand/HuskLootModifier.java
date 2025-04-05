package com.mars.huskssand;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class HuskLootModifier extends LootModifier {
    private static final Item sandDrop = Registry.ITEM.get(new ResourceLocation("sand"));

    public HuskLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext lootContext) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(lootContext)) {
                return generatedLoot;
            }
        }

        Random random = lootContext.getRandom();
        int rolls = random.nextInt(HusksSandConfig.min_rolls, HusksSandConfig.max_rolls);
        Player player = lootContext.getParamOrNull(LootContextParams.LAST_DAMAGE_PLAYER);

        if(player != null) {
            ItemStack weapon = player.getMainHandItem();
            int lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MOB_LOOTING, weapon);

            if (lootingLevel > 0)
                rolls = rolls + random.nextInt(0, lootingLevel);
        }

        generatedLoot.add(new ItemStack(sandDrop, rolls));

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<HuskLootModifier> {
        @Override
        public HuskLootModifier read(ResourceLocation location, JsonObject object,
                                     LootItemCondition[] ailootcondition) {
            return new HuskLootModifier(ailootcondition);
        }

        @Override
        public JsonObject write(HuskLootModifier instance) {
            return new JsonObject();
        }
    }
}
