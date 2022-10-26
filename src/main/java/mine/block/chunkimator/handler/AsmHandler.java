package mine.block.chunkimator.handler;

import mine.block.chunkimator.client.ChunkimatorClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;

/**
 * This class acts as a "middle man" between Minecraft's transformed classes and
 * the {@link AnimationHandler}.
 *
 * @author lumien231
 */
public final class AsmHandler {

	private AsmHandler() {}

	private static final AnimationHandler HANDLER = ChunkimatorClient.animationHandler;

	/**
	 * Calls {@link AnimationHandler#setOrigin(ChunkBuilder.BuiltChunk, BlockPos)} with
	 * the given parameters.
	 *
	 * <p>The {@link ChunkBuilder.BuiltChunk} transformer invokes this method, inserting
	 * the call in {@link ChunkBuilder.BuiltChunk#setOrigin(int, int, int)}.</p>
	 *
	 * @param renderChunk The {@link ChunkBuilder.BuiltChunk} instance.
	 * @param x The {@code x-coordinate} for the origin.
	 * @param y The {@code y-coordinate} for the origin.
	 * @param z The {@code z-coordinate} for the origin.
	 */
	public static void setOrigin(ChunkBuilder.BuiltChunk renderChunk, int x, int y, int z) {
		HANDLER.setOrigin(renderChunk, new BlockPos(x, y, z));
	}

	/**
	 * Calls {@link AnimationHandler#preRender(PreRenderContext)}
	 * with the given parameters.
	 *
	 * <p>The {@link WorldRenderer} transformer invokes this method, replacing the default
	 * {@link GlUniform#set(float, float, float)} call in
	 *
	 * @param uniform The chunk offset {@link GlUniform} object.
	 * @param x The final x-coordinate for the chunk (where it should end up).
	 * @param y The final y-coordinate for the chunk (where it should end up).
	 * @param z The final z-coordinate for the chunk (where it should end up).
	 * @param renderChunk The {@link ChunkBuilder.BuiltChunk} instance.
	 */
	public static void preRenderChunk(final GlUniform uniform, final float x, final float y, final float z,
									  final ChunkBuilder.BuiltChunk renderChunk) {
		HANDLER.preRender(new PreRenderContext(renderChunk, uniform, x, y, z));
	}

}
