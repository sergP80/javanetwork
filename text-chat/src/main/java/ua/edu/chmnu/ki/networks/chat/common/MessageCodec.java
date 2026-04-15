package ua.edu.chmnu.ki.networks.chat.common;

public final class MessageCodec {

    private static final String SEPARATOR = "|";

    private MessageCodec() {
    }

    public static String serialize(ChatMessage message) {
        return escape(message.type().name()) + SEPARATOR
                + escape(nullToEmpty(message.from())) + SEPARATOR
                + escape(nullToEmpty(message.to())) + SEPARATOR
                + escape(nullToEmpty(message.to())) + SEPARATOR
                + escape(nullToEmpty(message.text())) + SEPARATOR
                + escape(nullToEmpty(message.pictureUrl()));
    }

    public static ChatMessage deserialize(String raw) {
        String[] parts = splitEscaped(raw, 5);

        MessageType type = MessageType.valueOf(unescape(parts[0]));
        String from = emptyToNull(unescape(parts[1]));
        String to = emptyToNull(unescape(parts[2]));
        String text = emptyToNull(unescape(parts[3]));
        String pictureUrl = emptyToNull(unescape(parts[4]));

        return new ChatMessage(type, from, to, text, pictureUrl);
    }

    private static String[] splitEscaped(String value, int expectedParts) {
        String[] result = new String[expectedParts];
        StringBuilder current = new StringBuilder();

        int partIndex = 0;
        boolean escaping = false;

        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);

            if (escaping) {
                current.append(ch);
                escaping = false;
                continue;
            }

            if (ch == '\\') {
                escaping = true;
                continue;
            }

            if (ch == '|' && partIndex < expectedParts - 1) {
                result[partIndex++] = current.toString();
                current.setLength(0);
                continue;
            }

            current.append(ch);
        }

        result[partIndex] = current.toString();

        for (int i = 0; i < expectedParts; i++) {
            if (result[i] == null) {
                result[i] = "";
            }
        }

        return result;
    }

    private static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("|", "\\|");
    }

    private static String unescape(String value) {
        StringBuilder result = new StringBuilder();
        boolean escaping = false;

        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);

            if (escaping) {
                result.append(ch);
                escaping = false;
                continue;
            }

            if (ch == '\\') {
                escaping = true;
                continue;
            }

            result.append(ch);
        }

        return result.toString();
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private static String emptyToNull(String value) {
        return value == null || value.isEmpty() ? null : value;
    }
}
