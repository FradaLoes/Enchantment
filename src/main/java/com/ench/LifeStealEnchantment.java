package com.ench;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
public class LifeStealEnchantment extends Enchantment {

    public LifeStealEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinCost(int level) {
        return 10 + (level - 1) * 20; // 附魔的最小经验成本
    }

    //    @Override
    public int getMaxCost(int level) {
        return super.getMinCost(level) + 50; // 附魔的最大经验成本
    }



    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (attacker.getHealth() < attacker.getMaxHealth()) {
            // 恢复生命值
            float healAmount = level * 2.0F; // 恢复的生命值，随着等级提高
            attacker.heal(healAmount);
        }

        // 恢复饥饿值
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            int hungerRecovery = level; // 根据附魔等级调整饥饿值恢复量
            player.getFoodData().eat(hungerRecovery, 0.0F); // 第二个参数为饱和度
        }
        // 使敌方减速和中毒
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;

            // 减速效果
            livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 * level, level - 1));

            // 中毒效果
            livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100 * level, 0));
        }


    }
    @Override
    public int getMaxLevel() {
        return 3122; // 最大等级设置为无限
    }
}



