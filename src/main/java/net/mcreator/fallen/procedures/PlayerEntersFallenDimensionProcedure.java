package net.mcreator.fallen.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import net.mcreator.fallen.block.FadedGrassBlock;
import net.mcreator.fallen.FallenModVariables;
import net.mcreator.fallen.FallenModElements;
import net.mcreator.fallen.FallenMod;

import java.util.Map;

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
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				FallenMod.LOGGER.warn("Failed to load dependency x for procedure PlayerEntersFallenDimension!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				FallenMod.LOGGER.warn("Failed to load dependency y for procedure PlayerEntersFallenDimension!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				FallenMod.LOGGER.warn("Failed to load dependency z for procedure PlayerEntersFallenDimension!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				FallenMod.LOGGER.warn("Failed to load dependency world for procedure PlayerEntersFallenDimension!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
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
						"You look around and find yourself in a grey dimension, after falling into the void. You decide to call the land Fallen. It would be best to try and locate a way to escape."),
						(false));
			}
		}
		if (((world.getBlockState(new BlockPos((int) x, (int) (y - 2), (int) z))).getBlock() == Blocks.AIR.getDefaultState().getBlock())) {
			{
				BlockPos _bp = new BlockPos((int) x, (int) (y - 2), (int) z);
				BlockState _bs = FadedGrassBlock.block.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
		}
		if (entity instanceof ServerPlayerEntity)
			((ServerPlayerEntity) entity).func_242111_a(((ServerPlayerEntity) entity).world.getDimensionKey(),
					new BlockPos((int) x, (int) y, (int) z), 0, true, false);
	}
}
