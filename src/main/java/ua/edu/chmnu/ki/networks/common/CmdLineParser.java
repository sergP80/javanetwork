package ua.edu.chmnu.ki.networks.common;

import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class CmdLineParser {
	private final Map<String, String> data;

	private CmdLineParser(String[] args) {
		this.data = parse(args);
	}

	public static CmdLineParser of(String[] args) {
		return new CmdLineParser(args);
	}

	public <T> T getOption(String key, Function<String, T> valueMapper) {
		return Optional.ofNullable(this.data.get(key)).map(valueMapper)
				.orElseThrow(() -> new IllegalArgumentException("Key " + key + " is not present"));
	}

	public <T> T getOption(String key, Function<String, T> valueMapper, T defaultValue) {
		return Optional.ofNullable(this.data.get(key)).map(valueMapper).orElse(defaultValue);
	}

	public String getStringOption(String key) {
		return getOption(key, s -> s);
	}

	public String getStringOption(String key, String defValue) {
		return getOption(key, s -> s, defValue);
	}

	public Integer getIntOption(String key) {
		return getOption(key, Integer::parseInt);
	}

	public Integer getIntOption(String key, Integer defValue) {
		return getOption(key, Integer::parseInt, defValue);
	}

	public Long getLongOption(String key) {
		return getOption(key, Long::parseLong);
	}

	public Long getLongOption(String key, Long defValue) {
		return getOption(key, Long::parseLong, defValue);
	}

	public Boolean getBooleanOption(String key) {
		return getOption(key, Boolean::parseBoolean);
	}

	public Boolean getBooleanOption(String key, Boolean defValue) {
		return getOption(key, Boolean::parseBoolean, defValue);
	}

	protected static Map<String, String> parse(String[] args) {
		Map<String, String> result = new HashMap<>();
		if (ArrayUtils.isEmpty(args)) {
			return result;
		}

		for (int i = 0; i < args.length; ) {
			if (args[i].startsWith("-") || args[i].startsWith("--")) {
				String key = args[i].substring(args[i].lastIndexOf('-') + 1);

				if (i < args.length - 1) {
					String value = args[++i];
					result.put(key, value);
				} else {
					++i;
				}

			} else {
				++i;
			}
		}
		return result;
	}

	public static String extractValue(String param, String paramPrefix) {
		int i = param.indexOf(paramPrefix);
		if (i >= 0) {
			return param.substring(i + 1);
		}
		return null;
	}
}
