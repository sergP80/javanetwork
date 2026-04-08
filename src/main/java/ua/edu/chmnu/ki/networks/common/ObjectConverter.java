package ua.edu.chmnu.ki.networks.common;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@UtilityClass
public class ObjectConverter {

    @SneakyThrows
    public static <T> byte[] convertToByte(T source) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream os = new ObjectOutputStream(baos)
        ) {
            os.writeObject(source);

            return baos.toByteArray();
        }
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T convertFromByte(byte[] source, int length) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(source, 0, length);
             ObjectInputStream is = new ObjectInputStream(bais)
        ) {
            return (T) is.readObject();
        }
    }
}
