package mine.block.chunkimator.mixin;

import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import mine.block.chunkimator.client.ChunkimatorClient;
import mine.block.chunkimator.handler.PreRenderContext;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.Uniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @author Harley O'Connor
 */
@Mixin(WorldRenderer.class)
public final class LevelRendererMixin {
    @Redirect(method = "renderLayer", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gl/GlUniform;set(FFF)V"
    ))
    private void preventDefaultOffset(GlUniform chunkOffset, float x, float y, float z) {
        // Since this doesn't allow local capture and we need access to the renderChunk we simply do nothing here
        // and replace this with our own logic in #preRenderChunk.
    }

    @Inject(method = "renderLayer", at = @At(
            value = "INVOKE",
            shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/client/gl/GlUniform;upload()V"
    ), locals = LocalCapture.CAPTURE_FAILHARD)
    private void preRenderChunk(RenderLayer renderType, MatrixStack poseStack, double camX, double camY, double camZ,
                                Matrix4f projectionMatrix, CallbackInfo ci, boolean notTranslucent,
                                ObjectListIterator<WorldRenderer.ChunkInfo> renderChunkIterator,
                                Shader shaderInstance, GlUniform chunkOffset,
                                WorldRenderer.ChunkInfo renderChunkInfo,
                                ChunkBuilder.BuiltChunk renderChunk, VertexBuffer chunkVertexBuffer,
                                BlockPos blockPos) {
        ChunkimatorClient.animationHandler.preRender(
                new PreRenderContext(
                        renderChunk,
                        chunkOffset,
                        (float) (blockPos.getX() - camX),
                        (float) (blockPos.getY() - camY),
                        (float) (blockPos.getZ() - camZ)
                )
        );
    }

}
