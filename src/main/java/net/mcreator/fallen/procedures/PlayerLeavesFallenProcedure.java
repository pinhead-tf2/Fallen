package net.mcreator.fallen.procedures;

import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.fallen.FallenModVariables;
import net.mcreator.fallen.FallenModElements;
import net.mcreator.fallen.FallenMod;

import java.util.Map;

@FallenModElements.ModElement.Tag
public class PlayerLeavesFallenProcedure extends FallenModElements.ModElement {
	public PlayerLeavesFallenProcedure(FallenModElements instance) {
		super(instance, 12);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				FallenMod.LOGGER.warn("Failed to load dependency entity for procedure PlayerLeavesFallen!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof ServerPlayerEntity)
			((ServerPlayerEntity) entity).func_242111_a(((ServerPlayerEntity) entity).world.getDimensionKey(),
					new BlockPos(
							(int) ((entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new FallenModVariables.PlayerVariables())).savedSleepCoordX),
							(int) ((entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new FallenModVariables.PlayerVariables())).savedSleepCoordY),
							(int) ((entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new FallenModVariables.PlayerVariables())).savedSleepCoordZ)),
					0, true, false);
	}
}
