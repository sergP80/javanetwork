package ua.edu.chmnu.ki.networks.rsv.serializer;

import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.serializer.impl.ClientHelloPacketSerializer;
import ua.edu.chmnu.ki.networks.rsv.serializer.impl.FrameChunkPacketSerializer;
import ua.edu.chmnu.ki.networks.rsv.serializer.impl.HeartBeatPacketSerializer;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UnknownFormatConversionException;
import java.util.function.Function;

public class PacketSerializeFactoryImpl implements PacketSerializeFactory {
    private final static Map<PacketType, Function<PacketFrame, PacketSerializer>> SERIALIZER_MAP = Map.of(
            PacketType.HELLO, ClientHelloPacketSerializer::new,
            PacketType.HEARTBEAT, HeartBeatPacketSerializer::new,
            PacketType.FRAME_CHUNK, FrameChunkPacketSerializer::new
    );

    @Override
    public PacketSerializer fetchBy(PacketFrame source) {
        Function<PacketFrame, PacketSerializer> serializerMapper = Optional.ofNullable(SERIALIZER_MAP.get(Objects.requireNonNull(source).type()))
                .orElseThrow(() -> new UnknownFormatConversionException("Cannot serialize " + source.type() + " packet"));

        return serializerMapper.apply(source);
    }
}
