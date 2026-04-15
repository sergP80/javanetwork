package ua.edu.chmnu.ki.networks.rsv.deserializer;

import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;
import ua.edu.chmnu.ki.networks.rsv.deserializer.impl.FrameChunkPacketDeserializer;
import ua.edu.chmnu.ki.networks.rsv.deserializer.impl.HeartBeatPacketDeserializer;
import ua.edu.chmnu.ki.networks.rsv.deserializer.impl.ClientHelloPacketDeserializer;

import java.net.DatagramPacket;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PacketDeserializeFactoryImpl implements PacketDeserializeFactory {

    private final static Map<PacketType, Function<DatagramPacket, PacketDeserializer>> DESERIALIZER_MAP = Map.of(
            PacketType.HELLO, ClientHelloPacketDeserializer::new,
            PacketType.HEARTBEAT, HeartBeatPacketDeserializer::new,
            PacketType.FRAME_CHUNK, FrameChunkPacketDeserializer::new
    );

    @Override
    public PacketDeserializer fetchBy(DatagramPacket packet) {
        PacketType packetType = PacketType.of(packet.getData()[0]);

        if (packetType == PacketType.UNKNOWN) {
            throw new IllegalArgumentException("Unsupported packet type");
        }

        Function<DatagramPacket, PacketDeserializer> mapper = Optional.ofNullable(DESERIALIZER_MAP.get(packetType))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported deserialization for " + packetType));

        return mapper.apply(packet);
    }
}
