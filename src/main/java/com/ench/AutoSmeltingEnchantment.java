package com.ench;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.ench.Main.AUTO_SMELTING;

@Mod.EventBusSubscriber(modid = "examplemod")
public class AutoSmeltingEnchantment extends Enchantment {

    public AutoSmeltingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        LivingEntity player = event.getPlayer();
        BlockState blockState = event.getState();
        Block block = blockState.getBlock();

        // 检查玩家的工具是否有自动熔炼附魔
        ItemStack tool = player.getMainHandItem();
        if (tool.isEnchantable() && tool.getEnchantmentLevel(AUTO_SMELTING.get()) > 0) {
            // 根据块的类型返回熔炼后的锭
            ItemStack result = getSmeltingResult(block);
            if (!result.isEmpty()) {
                // 将物品添加到玩家的物品栏中
                if (player instanceof Player) {
                    ((Player) player).getInventory().add(result); // 使用正确的方法添加物品
                }
            }
        }
    }

    private static ItemStack getSmeltingResult(Block block) {
        // 根据块的类型返回熔炼后的锭
        if (block == Blocks.IRON_ORE) {
            return new ItemStack(Items.IRON_INGOT);
        } else if (block == Blocks.GOLD_ORE) {
            return new ItemStack(Items.GOLD_INGOT);
        }
        // 你可以继续添加其他矿石的逻辑
        return ItemStack.EMPTY;
    }
}
