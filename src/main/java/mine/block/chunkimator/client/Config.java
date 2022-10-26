package mine.block.chunkimator.client;

import io.wispforest.owo.config.annotation.Modmenu;
import mine.block.chunkimator.config.AnimationMode;
import mine.block.chunkimator.config.EasingFunction;

@Modmenu(modId = "chunkimator")
@io.wispforest.owo.config.annotation.Config(name = "chunkimator", wrapperName = "ChunkimatorConfig")
public class Config {
    public AnimationMode animationMode = AnimationMode.BELOW;
    public int animationDurationMs = 1000;
    public boolean disableAroundPlayer = false;
    public EasingFunction easing = EasingFunction.SINE;
}
