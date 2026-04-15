package ua.edu.chmnu.ki.networks.rsv.codec;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public class FrameEncoderFactoryImpl implements FrameEncoderFactory {

    private static final Map<String, Function<Float, FrameEncoder>> FRAME_ENCODER_MAP = Map.of(
      "JPEG", JpegFrameEncoder::new,
      "JPG", JpegFrameEncoder::new
    );

    @Override
    public FrameEncoder fetchBy(String type, float quality) {
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("Empty encoder type");
        }

        Function<Float, FrameEncoder> encoderMapper = Optional.ofNullable(FRAME_ENCODER_MAP.get(type.toUpperCase()))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported encoder type: " + type));

        return encoderMapper.apply(quality);
    }
}
