package mine.block.chunkimator.handler;


import mine.block.chunkimator.client.ChunkimatorClient;
import mine.block.chunkimator.config.AnimationMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

import java.util.Objects;
import java.util.WeakHashMap;

/**
 * This class handles setting up and rendering the animations.
 *
 * @author lumien231
 */
public final class AnimationHandler {

	private final MinecraftClient mc = MinecraftClient.getInstance();
	private final WeakHashMap<ChunkBuilder.BuiltChunk, AnimationData> timeStamps = new WeakHashMap<>();

	public void preRender(PreRenderContext context) {
		final var animationData = timeStamps.get(context.renderChunk());

		if (animationData == null) {
			context.uniform().set(context.x(), context.y(), context.z());
			return;
		}

		final var mode = ChunkimatorClient.CONFIG.animationMode();
		final int animationDuration = ChunkimatorClient.CONFIG.animationDurationMs();

		long time = animationData.timeStamp;

		// If preRender hasn't been called on this chunk yet, prepare to start the animation.
		if (time == -1L) {
			time = System.currentTimeMillis();
			animationData.timeStamp = time;
			mode.prepareConsumer().accept(context, animationData);
		}

		final long timeDif = System.currentTimeMillis() - time;

		if (timeDif < animationDuration) {
			ChunkimatorClient.CONFIG.animationMode().contextConsumer().accept(new AnimationContext(
					context.renderChunk(),
					context.uniform(),
					context.x(),
					context.y(),
					context.z(),
					animationData,
					context.renderChunk().getOrigin(),
					timeDif,
					AnimationContext.LevelContext.from(Objects.requireNonNull(this.mc.world))
			));
		} else {
			context.uniform().set(context.x(), context.y(), context.z());
			this.timeStamps.remove(context.renderChunk());
		}
	}

	public void setOrigin(final ChunkBuilder.BuiltChunk renderChunk, final BlockPos pos) {
		if (this.mc.player == null)
			return;

		final BlockPos zeroedPlayerPos = getZeroedPlayerPos(this.mc.player);
		final BlockPos zeroedCenteredChunkPos = getZeroedCenteredChunkPos(pos);

		if (!ChunkimatorClient.CONFIG.disableAroundPlayer() || zeroedPlayerPos.getSquaredDistance(zeroedCenteredChunkPos) > (64 * 64)) {
			timeStamps.put(renderChunk, new AnimationData(-1L, ChunkimatorClient.CONFIG.animationMode() == AnimationMode.HORIZONTAL_SLIDE ?
					getChunkFacing(zeroedPlayerPos.subtract(zeroedCenteredChunkPos)) : null));
		} else {
			timeStamps.remove(renderChunk);
		}
	}

	/**
	 * Gets the given player's position, setting their {@code y-coordinate} to {@code 0}.
	 *
	 * @param player The {@link ClientPlayerEntity} instance.
	 * @return The zeroed {@link BlockPos}.
	 */
	public static BlockPos getZeroedPlayerPos(final ClientPlayerEntity player) {
		final BlockPos playerPos = new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ());
		return playerPos.add(0, -player.getBlockY(), 0);
	}

	/**
	 * Gets the given {@link BlockPos} for the chunk, setting its {@code y-coordinate} to
	 * {@code 0} and offsetting its {@code x} and {@code y-coordinate} to by {@code 8}.
	 *
	 * @param position The {@link BlockPos} of the chunk.
	 * @return The zeroed, centered {@link BlockPos}.
	 */
	public static BlockPos getZeroedCenteredChunkPos(final BlockPos position) {
		return position.add(8, -position.getY(), 8);
	}

	/**
	 * Gets the direction the chunk is facing based on the given {@link Vec3i}
	 * from the relevant position to the chunk.
	 *
	 * @param dif The {@link Vec3i} distance from the relevant position to the chunk.
	 * @return The {@link Direction} of the chunk relative to the {@code dif}.
	 */
	public static Direction getChunkFacing(final Vec3i dif) {
		final int difX = Math.abs(dif.getX());
		final int difZ = Math.abs(dif.getZ());

		return difX > difZ ? dif.getX() > 0 ? Direction.EAST : Direction.WEST : dif.getZ() > 0 ? Direction.SOUTH : Direction.NORTH;
	}

	public void clear () {
		// These should be cleared by GC, but just in case.
		this.timeStamps.clear();
	}

	public static class AnimationData {
		public long timeStamp;
		public Direction chunkFacing;

		public AnimationData(final long timeStamp, final Direction chunkFacing) {
			this.timeStamp = timeStamp;
			this.chunkFacing = chunkFacing;
		}
	}

}
