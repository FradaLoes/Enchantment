package com.ench;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ThornShieldEnchantment extends Enchantment {

    private static final String NBT_KEY = "ThornShieldLevel"; // NBT 键
    private static final double DAMAGE_REFLECT_PER_LEVEL = 0.2; // 每级反弹的伤害比例

    public ThornShieldEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.OFFHAND});
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 331231; // 最大等级
    }

    // 处理反弹伤害事件
    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity(); // 受伤实体
        if (target instanceof Player player) {
            ItemStack shield = player.getItemBySlot(EquipmentSlot.OFFHAND);
            if (!shield.isEmpty()) {
                CompoundTag nbt = shield.getTag();
                if (nbt != null && nbt.contains(NBT_KEY)) {
                    int level = nbt.getInt(NBT_KEY); // 从 NBT 中获取等级
                    double damage = event.getAmount() * DAMAGE_REFLECT_PER_LEVEL * level; // 计算反弹伤害
                    if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                        attacker.hurt(event.getSource(), (float) damage); // 反弹伤害给攻击者
                    }
                }
            }
        }
    }

    // 设置 NBT 标签的方法
    public static void setThornShieldLevel(ItemStack shield, int level) {
        CompoundTag nbt = shield.getOrCreateTag();
        nbt.putInt(NBT_KEY, level);
        shield.setTag(nbt);
    }
}
