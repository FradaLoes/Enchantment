package com.ench;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HealthBoostEnchantment extends Enchantment {

    private static final String NBT_KEY = "HealthBoostLevel";
    private static final double HEALTH_BOOST_PER_LEVEL = 2.0; // 每级附魔增加的生命值

    public HealthBoostEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
        });
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 5; // 最大等级
    }

    // 玩家装备物品时触发
    @SubscribeEvent
    public void onItemEquip(PlayerEvent.ItemCraftedEvent event) {
        LivingEntity entity = (LivingEntity) event.getEntity(); // 获取玩家实体
        updateHealth(entity); // 更新生命值
    }

    private void updateHealth(LivingEntity entity) {
        double totalHealthBoost = 0;

        // 检查每个装备槽
        for (ItemStack armor : entity.getArmorSlots()) {
            if (!armor.isEmpty()) {
                CompoundTag nbt = armor.getTag();
                if (nbt != null && nbt.contains(NBT_KEY)) {
                    int level = nbt.getInt(NBT_KEY);
                    totalHealthBoost += level * HEALTH_BOOST_PER_LEVEL; // 根据附魔等级增加生命值
                }
            }
        }

        // 直接设置最大生命值
        double newMaxHealth = 20.0 + totalHealthBoost; // 20.0 是默认最大生命值
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(newMaxHealth);
        entity.setHealth((float) Math.min(entity.getHealth(), newMaxHealth)); // 更新当前生命值

        System.out.println("New Max Health: " + newMaxHealth); // 调试输出
    }

    // 玩家每个 tick 触发
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        LivingEntity player = event.player;
        if (!player.level().isClientSide) { // 确保在服务器端
            double currentHealth = player.getHealth();
            double newHealth = Math.min(currentHealth + 1, player.getMaxHealth()); // 每个 tick 增加1生命值
            player.setHealth((float) newHealth); // 更新当前生命值
        }
    }
}
