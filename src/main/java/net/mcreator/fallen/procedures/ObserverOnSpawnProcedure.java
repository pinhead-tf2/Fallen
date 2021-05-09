package net.mcreator.fallen.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.fallen.FallenModElements;
import net.mcreator.fallen.FallenMod;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@FallenModElements.ModElement.Tag
public class ObserverOnSpawnProcedure extends FallenModElements.ModElement {
	public ObserverOnSpawnProcedure(FallenModElements instance) {
		super(instance, 18);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				FallenMod.LOGGER.warn("Failed to load dependency entity for procedure ObserverOnSpawn!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				FallenMod.LOGGER.warn("Failed to load dependency x for procedure ObserverOnSpawn!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				FallenMod.LOGGER.warn("Failed to load dependency y for procedure ObserverOnSpawn!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				FallenMod.LOGGER.warn("Failed to load dependency z for procedure ObserverOnSpawn!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				FallenMod.LOGGER.warn("Failed to load dependency world for procedure ObserverOnSpawn!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		{
			List<? extends PlayerEntity> _players = new ArrayList<>(world.getPlayers());
			for (Entity entityiterator : _players) {
				if (((entityiterator.world.getDimensionKey()) == (world instanceof World ? (((World) world).getDimensionKey()) : World.OVERWORLD))) {
					if (entityiterator instanceof PlayerEntity && !entityiterator.world.isRemote()) {
						((PlayerEntity) entityiterator)
								.sendStatusMessage(new StringTextComponent("\u00A78\u00A7oYou feel like you're being watched."), (false));
					}
				}
			}
		}
		if (world instanceof World && !world.isRemote()) {
			((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("fallen:howling_wind")),
					SoundCategory.NEUTRAL, (float) 255, (float) 1);
		} else {
			((World) world).playSound(x, y, z,
					(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("fallen:howling_wind")),
					SoundCategory.NEUTRAL, (float) 255, (float) 1, false);
		}
		new Object() {
			private int ticks = 0;
			private float waitTicks;
			private IWorld world;
			public void start(IWorld world, int waitTicks) {
				this.waitTicks = waitTicks;
				MinecraftForge.EVENT_BUS.register(this);
				this.world = world;
			}

			@SubscribeEvent
			public void tick(TickEvent.ServerTickEvent event) {
				if (event.phase == TickEvent.Phase.END) {
					this.ticks += 1;
					if (this.ticks >= this.waitTicks)
						run();
				}
			}

			private void run() {
				if (world instanceof ServerWorld) {
					((ServerWorld) world).spawnParticle(ParticleTypes.SOUL, x, y, z, (int) 5, 3, 3, 3, 1);
				}
				if (!entity.world.isRemote())
					entity.remove();
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		}.start(world, (int) 60);
	}
}
