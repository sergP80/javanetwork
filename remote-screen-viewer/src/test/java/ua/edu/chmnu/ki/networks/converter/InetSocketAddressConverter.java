package ua.edu.chmnu.ki.networks.converter;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.TypedArgumentConverter;

import java.net.InetSocketAddress;

public class InetSocketAddressConverter extends TypedArgumentConverter<String, InetSocketAddress> {

    protected InetSocketAddressConverter() {
        super(String.class, InetSocketAddress.class);
    }

    @Override
    protected InetSocketAddress convert(String source) throws ArgumentConversionException {
        try {
            String[] parts = source.split(":");
            String host = parts[0];
            int port = Integer.parseInt(parts[1]);
            return new InetSocketAddress(host, port);
        } catch (Exception e) {
            throw new ArgumentConversionException("Ошибка парсинга InetSocketAddress: " + source, e);
        }
    }
}
