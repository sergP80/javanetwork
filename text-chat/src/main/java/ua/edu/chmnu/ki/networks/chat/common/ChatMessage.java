package ua.edu.chmnu.ki.networks.chat.common;

public record ChatMessage(
        MessageType type,
        String from,
        String text,
        String pictureUrl
) {

    public ChatMessage {
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
    }

    public static ChatMessage system(String text) {
        return new ChatMessage(MessageType.SYSTEM, "SYSTEM", text, null);
    }

    public static ChatMessage text(String from, String text) {
        return new ChatMessage(MessageType.TEXT, from, text, null);
    }

    public static ChatMessage picture(String from, String pictureUrl) {
        return new ChatMessage(MessageType.PICTURE, from, null, pictureUrl);
    }

    public static ChatMessage textAndPicture(String from, String text, String pictureUrl) {
        return new ChatMessage(MessageType.TEXT_AND_PICTURE, from, text, pictureUrl);
    }
}
