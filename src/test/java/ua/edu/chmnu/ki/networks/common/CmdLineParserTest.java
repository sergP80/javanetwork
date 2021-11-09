package ua.edu.chmnu.ki.networks.common;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CmdLineParserTest {

	@ParameterizedTest
	@MethodSource("provideFixturesForArrayOptions")
	public void shouldSuccessParseArrayOptions(String[] args, String key, List<String> expectedValues) {
		var parser = spy(CmdLineParser.of(args));
		var actualValues = parser.getStringArrayOption(key);

		assertEquals(expectedValues, actualValues);
		verify(parser).getStringArrayOption(eq(key), anyString());
	}

	static Stream<Arguments> provideFixturesForArrayOptions() {
		return Stream.of(
				Arguments.of(
						new String[]{"-key1", "value1;value2;value3"},
						"key1",
						Arrays.asList("value1", "value2", "value3")
				),
				Arguments.of(
						new String[]{"--key2", "value1,value2,value3"},
						"key2",
						Arrays.asList("value1", "value2", "value3")
				),
				Arguments.of(
						new String[]{"--key3", "value1:value2:value3"},
						"key3",
						Arrays.asList("value1", "value2", "value3")
				)
		);
	}
}