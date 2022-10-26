package mine.block.chunkimator.handler;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

/**
 * Holds context parameters required for animating chunks.
 *
 * @author Harley O'Connor
 */
public record AnimationContext(
        ChunkBuilder.BuiltChunk renderChunk,
        GlUniform uniform,
        float x,
        float y,
        float z,
        AnimationHandler.AnimationData animationData,
        BlockPos origin,
        float timeDif,
        LevelContext levelContext
) {

    public record LevelContext(
            double horizonHeight,
            int minY,
            int maxY
    ) {

        public static LevelContext from(ClientWorld level) {
            return new LevelContext(level.getLevelProperties().getSkyDarknessHeight(level), level.getDimension().minY(),
                    level.getDimension().minY() + level.getDimension().height());
        }

    }

}
