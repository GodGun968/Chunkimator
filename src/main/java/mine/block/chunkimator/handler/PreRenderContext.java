package mine.block.chunkimator.handler;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;

/**
 * Holds context gathered from the {@link WorldRenderer} required for pre-rendering
 * the chunk.
 *
 * @author Harley O'Connor
 */
public record PreRenderContext(
        ChunkBuilder.BuiltChunk renderChunk,
        GlUniform uniform,
        float x,
        float y,
        float z
) { }
