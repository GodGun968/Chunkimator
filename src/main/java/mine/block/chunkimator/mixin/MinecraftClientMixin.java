package mine.block.chunkimator.mixin;

import mine.block.chunkimator.client.ChunkimatorClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Nullable public ClientWorld world;
    @Unique public boolean firstCapture = true;
    @Unique public ClientWorld previousWorld;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickHEADInject(CallbackInfo ci) {
        if(this.world != previousWorld && !firstCapture) {
            // unload
            ChunkimatorClient.animationHandler.clear();
        }

        firstCapture = false;
        this.previousWorld = world;
    }
}
