package net.mcreator.fallen.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.fallen.block.FadedGrassBlock;
import net.mcreator.fallen.FallenModVariables;
import net.mcreator.fallen.FallenModElements;
import net.mcreator.fallen.FallenMod;

import java.util.Map;
import java.util.Collections;

@FallenModElements.ModElement.Tag
public class PlayerEntersFallenDimensionProcedure extends FallenModElements.ModElement {
	public PlayerEntersFallenDimensionProcedure(FallenModElements instance) {
		super(instance, 10);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				FallenMod.LOGGER.warn("Failed to load dependency entity for procedure PlayerEntersFallenDimension!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				FallenMod.LOGGER.warn("Failed to load dependency world for procedure PlayerEntersFallenDimension!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		IWorld world = (IWorld) dependencies.get("world");
		boolean spotFound = false;
		double yToCheck = 0;
		double checkingX = 0;
		double checkingZ = 0;
		FallenMod.LOGGER.info("Attempting to locate a safe spawnpoint...");
		if (entity instanceof PlayerEntity) {
			((PlayerEntity) entity).abilities.disableDamage = (true);
			((PlayerEntity) entity).sendPlayerAbilities();
		}
		while (((spotFound) == (false))) {
			yToCheck = (double) 128;
			checkingX = (double) (Math.random() * 100);
			checkingZ = (double) (Math.random() * 100);
			for (int index1 = 0; index1 < (int) (80); index1++) {
				yToCheck = (double) ((yToCheck) - 1);
				if (((world.getBlockState(new BlockPos((int) (checkingX), (int) (yToCheck), (int) (checkingZ)))).getBlock() == FadedGrassBlock.block
						.getDefaultState().getBlock())) {
					FallenMod.LOGGER.info("Located land! Teleporting player...");
					spotFound = (boolean) (true);
					if (entity instanceof PlayerEntity) {
						((PlayerEntity) entity).abilities.disableDamage = (true);
						((PlayerEntity) entity).sendPlayerAbilities();
					}
					if (entity instanceof ServerPlayerEntity)
						((ServerPlayerEntity) entity).func_242111_a(((ServerPlayerEntity) entity).world.getDimensionKey(),
								new BlockPos((int) (checkingX), (int) (yToCheck), (int) (checkingZ)), 0, true, false);
					{
						Entity _ent = entity;
						_ent.setPositionAndUpdate((checkingX), (yToCheck), (checkingZ));
						if (_ent instanceof ServerPlayerEntity) {
							((ServerPlayerEntity) _ent).connection.setPlayerLocation((checkingX), (yToCheck), (checkingZ), _ent.rotationYaw,
									_ent.rotationPitch, Collections.emptySet());
						}
					}
					break;
				}
			}
		}
		if ((((entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new FallenModVariables.PlayerVariables())).hasVisitedFallen) == (false))) {
			{
				boolean _setval = (boolean) (true);
				entity.getCapability(FallenModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.hasVisitedFallen = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(
						"\u00A78\u00A7oYou look around and find yourself in a grey dimension, after falling into the void. It would be best to try and locate a way to escape."),
						(false));
			}
		}
	}
}
