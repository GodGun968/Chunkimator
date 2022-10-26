package mine.block.chunkimator.client;

import mine.block.chunkimator.config.AnimationMode;
import mine.block.chunkimator.config.EasingFunction;
import mine.block.chunkimator.handler.AnimationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ChunkimatorClient implements ClientModInitializer {
    public static AnimationHandler animationHandler = new AnimationHandler();

    @Override
    public void onInitializeClient() {

    }

    public static mine.block.chunkimator.client.ChunkimatorConfig CONFIG = mine.block.chunkimator.client.ChunkimatorConfig.createAndLoad();
}
