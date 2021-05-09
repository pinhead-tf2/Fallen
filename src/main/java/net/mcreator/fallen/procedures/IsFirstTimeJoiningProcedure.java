package net.mcreator.fallen.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.entity.Entity;

import net.mcreator.fallen.FallenModVariables;
import net.mcreator.fallen.FallenModElements;
import net.mcreator.fallen.FallenMod;

import java.util.Map;
import java.util.HashMap;

@FallenModElements.ModElement.Tag
public class IsFirstTimeJoiningProcedure extends FallenModElements.ModElement {
	public IsFirstTimeJoiningProcedure(FallenModElements instance) {
		super(instance, 20);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				FallenMod.LOGGER.warn("Failed to load dependency entity for procedure IsFirstTimeJoining!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				FallenMod.LOGGER.warn("Failed to load dependency x for procedure IsFirstTimeJoining!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				FallenMod.LOGGER.warn("Failed to load dependency y for procedure IsFirstTimeJoining!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				FallenMod.LOGGER.warn("Failed to load dependency z for procedure IsFirstTimeJoining!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		if ((((entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new FallenModVariables.PlayerVariables())).newToWorld) == (true))) {
			{
				boolean _setval = (boolean) (false);
				entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.newToWorld = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = (double) x;
				entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedSleepCoordX = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = (double) y;
				entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedSleepCoordY = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			{
				double _setval = (double) z;
				entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.savedSleepCoordZ = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		Entity entity = event.getPlayer();
		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("x", entity.getPosX());
		dependencies.put("y", entity.getPosY());
		dependencies.put("z", entity.getPosZ());
		dependencies.put("world", entity.world);
		dependencies.put("entity", entity);
		dependencies.put("event", event);
		this.executeProcedure(dependencies);
	}
}
