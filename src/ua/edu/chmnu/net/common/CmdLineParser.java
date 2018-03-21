package ua.edu.chmnu.net.common;

public class CmdLineParser {
    public static String extractValue(String param, String paramPrefix) {
        int i = param.indexOf(paramPrefix);
        if (i >= 0) {
            return param.substring(i + 1);
        }
        return null;
    }
}
