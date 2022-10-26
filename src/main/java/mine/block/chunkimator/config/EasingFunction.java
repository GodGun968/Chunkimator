package mine.block.chunkimator.config;

import com.mojang.datafixers.util.Function4;
import mine.block.chunkimator.easings.*;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Harley O'Connor
 */
public enum EasingFunction {
    LINEAR(Linear::easeOut),
    QUAD(Quad::easeOut),
    CUBIC(Cubic::easeOut),
    QUART(Quart::easeOut),
    QUINT(Quint::easeOut),
    EXPO(Expo::easeOut),
    SINE(Sine::easeOut),
    CIRC(Circ::easeOut),
    BACK(Back::easeOut),
    BOUNCE(Bounce::easeOut),
    ELASTIC(Elastic::easeOut);

    private final Function4<Float, Float, Float, Float, Float> easeOutFunc;

    public static void main(String[] args) {
        for (EasingFunction value : values()) {
            System.out.println("\"text.config.chunkimator.option.easing.value."+value.name().toLowerCase()+"\": \""+ StringUtils.capitalize(value.name().toLowerCase())+"\",");
        }
    }

    EasingFunction(Function4<Float, Float, Float, Float, Float> easeOutFunc) {
        this.easeOutFunc = easeOutFunc;
    }

    public Function4<Float, Float, Float, Float, Float> easeOutFunc() {
        return easeOutFunc;
    }
}