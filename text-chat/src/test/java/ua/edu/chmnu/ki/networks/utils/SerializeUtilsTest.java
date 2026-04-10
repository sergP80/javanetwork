package ua.edu.chmnu.ki.networks.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class SerializeUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "22, Hello",
            "55, Bank"
    })
    void shouldSerializeAndViseVersa(int x, String s) {
        TestData expected = new TestData(x, s);

        byte[] bytes = SerializeUtils.serialize(expected);

        assertNotNull(bytes);

        TestData actual = SerializeUtils.deserialize(bytes);

        assertEquals(expected, actual);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestData implements Serializable {

        int x;

        String s;
    }
}