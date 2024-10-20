package com.ench;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod("examplemod")
public class Main {
    // 创建附魔的注册表
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "examplemod");
    public static final RegistryObject<LifeStealEnchantment> LIFE_STEAL = ENCHANTMENTS.register("life_steal", LifeStealEnchantment::new);
    private static Object AutoSmeltingEnchantment;
    public static final RegistryObject<AutoSmeltingEnchantment> AUTO_SMELTING = ENCHANTMENTS.register("auto_smelting", AutoSmeltingEnchantment::new);
    public static final RegistryObject<HealthBoostEnchantment> HEALTH_BOOST = ENCHANTMENTS.register("health_boost", HealthBoostEnchantment::new);
    public static final RegistryObject<ThornShieldEnchantment> THORN_SHIELD = ENCHANTMENTS.register("thorn_shield", ThornShieldEnchantment::new);

    public Main() {
        // 注册附魔
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());


    }
}
