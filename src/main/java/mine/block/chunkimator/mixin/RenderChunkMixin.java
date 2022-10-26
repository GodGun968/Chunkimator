package mine.block.chunkimator.mixin;

import mine.block.chunkimator.client.ChunkimatorClient;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Harley O'Connor
 */
@Mixin(ChunkBuilder.BuiltChunk.class)
public final class RenderChunkMixin {

    @Inject(method = "setOrigin", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk;clear()V"
    ))
    public void setOrigin(int x, int y, int z, CallbackInfo ci) {
        ChunkimatorClient.animationHandler.setOrigin(
                (ChunkBuilder.BuiltChunk) (Object) this,
                new BlockPos(x, y, z)
        );
    }

}
