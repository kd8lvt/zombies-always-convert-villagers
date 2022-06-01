package com.kd8lvt.zombiesalwaysconvertvillagers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.*;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((ServerWorld world, Entity entity, LivingEntity killedEntity) -> {
				if (entity.getType().equals(EntityType.ZOMBIE) || entity.getType().equals(EntityType.ZOMBIE_VILLAGER) || entity.getType().equals(EntityType.DROWNED) || entity.getType().equals(EntityType.ZOMBIFIED_PIGLIN)) {
					if (killedEntity.getType().equals(EntityType.VILLAGER)) {
						VillagerEntity vEnt = (VillagerEntity) killedEntity;
						VillagerData data = vEnt.getVillagerData();
						TradeOfferList offers = vEnt.getOffers();
						int xp = vEnt.getExperience();

						ZombieVillagerEntity zEnt = vEnt.convertTo(EntityType.ZOMBIE_VILLAGER, true);

						zEnt.setHealth(zEnt.getMaxHealth());
						zEnt.setVillagerData(data);
						zEnt.setOfferData(offers.toNbt());
						zEnt.setXp(xp);
					}
				}
		});
	}
}
