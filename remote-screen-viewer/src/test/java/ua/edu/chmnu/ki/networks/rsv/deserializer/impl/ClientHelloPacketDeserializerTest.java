package ua.edu.chmnu.ki.networks.rsv.deserializer.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.edu.chmnu.ki.networks.rsv.protocol.ClientHello;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketFrame;
import ua.edu.chmnu.ki.networks.rsv.protocol.PacketType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientHelloPacketDeserializerTest {

    @Mock
    private DatagramPacket datagramPacket;

    @ParameterizedTest
    @CsvSource({
            "Hello, world!"
    })
    void shouldSuccessDeserializeMessage(String message) throws IOException {
        byte[] bytes = convertTo(PacketType.HELLO, message);
        when(datagramPacket.getData()).thenReturn(bytes);
        when(datagramPacket.getLength()).thenReturn(bytes.length);

        PacketType packetType = PacketType.of(bytes[0]);

        assertEquals(PacketType.HELLO, packetType);

        PacketFrame actual = new ClientHelloPacketDeserializer(datagramPacket).deserialize();

        assertNotNull(actual);

        assertAll(
                () -> assertEquals(PacketType.HELLO, actual.type()),
                () -> assertEquals(message, ((ClientHello) actual).clientName())
        );
    }

    private byte[] convertTo(PacketType packetType, String message) throws IOException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos)) {

            dos.writeByte(packetType.getType());
            dos.write(message.getBytes(StandardCharsets.UTF_8));
            dos.flush();

            return baos.toByteArray();
        }
    }
}