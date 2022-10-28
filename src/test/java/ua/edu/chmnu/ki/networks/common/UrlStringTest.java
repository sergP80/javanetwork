package ua.edu.chmnu.ki.networks.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlStringTest {

    @ParameterizedTest
    @CsvSource(
            delimiter = '|',
            value = {
                    "http://book-store.com?q=13453&name='Java book'"
            }
    )
    void shouldCorrectEncodString(String rawString) {
        var encodedString = URLEncoder.encode(rawString, StandardCharsets.UTF_8);
        var originalString = URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
        Assertions.assertEquals(rawString, originalString);
    }
}
