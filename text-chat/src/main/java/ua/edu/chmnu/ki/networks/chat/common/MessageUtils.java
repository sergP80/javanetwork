package ua.edu.chmnu.ki.networks.chat.common;

import org.apache.commons.lang3.StringUtils;

public class MessageUtils {

    public static final String PIC_COMMAND = "/pic ";
    public static final String BOTH_COMMAND = "/both ";
    public static final String QUIT_COMMAND = "/quit";
    public static final String PRIVATE_COMMAND = "/pm ";

    public static ChatMessage toMessage(String username, String line) {
        if (line == null) {
            return null;
        }

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        if (QUIT_COMMAND.equalsIgnoreCase(trimmed)) {

            return ChatMessage.text(username, QUIT_COMMAND);
        }

        if (trimmed.startsWith(PIC_COMMAND)) {
            String url = trimmed.substring(PIC_COMMAND.length()).trim();
            if (url.isEmpty()) {
                System.out.println("Picture URL is empty.");
                return null;
            }
            return ChatMessage.picture(username, url);
        }

        if (trimmed.startsWith(BOTH_COMMAND)) {
            String payload = trimmed.substring(BOTH_COMMAND.length());
            int separatorIndex = payload.indexOf('|');

            if (separatorIndex < 0) {
                System.out.println("Use format: /both <text> | <url>");
                return null;
            }

            String text = payload.substring(0, separatorIndex).trim();
            String url = payload.substring(separatorIndex + 1).trim();

            if (text.isEmpty() && url.isEmpty()) {
                System.out.println("Both text and picture URL are empty.");
                return null;
            }

            return ChatMessage.textAndPicture(username, text, url);
        }

        if (trimmed.startsWith(PRIVATE_COMMAND)) {
            String payload = trimmed.substring(PRIVATE_COMMAND.length());
            int spaceIndex = payload.indexOf(' ');

            if (spaceIndex < 0) {
                System.out.println("Use format: /pm <username> <message>");
                return null;
            }

            String recipient = payload.substring(0, spaceIndex).trim();
            String text = payload.substring(spaceIndex + 1).trim();
//            System.out.println(recipient + ' ' + text);

            if (recipient.isEmpty() || text.isEmpty()) {
                System.out.println("Recipient or message cannot be empty.");
                return null;
            }

            return ChatMessage.privateText(username, recipient, text);
        }

        return ChatMessage.text(username, trimmed);
    }

    public static ChatMessage normalizeIncomingMessage(String username, ChatMessage incoming) {
        if (incoming == null) {
            return null;
        }

        switch (incoming.type()) {
            case TEXT:
                if (StringUtils.isBlank(incoming.text())) {
                    return null;
                }
                return ChatMessage.text(username, incoming.text());

            case PICTURE:
                if (StringUtils.isBlank(incoming.text())) {
                    return null;
                }
                return ChatMessage.picture(username, incoming.pictureUrl());

            case TEXT_AND_PICTURE:
                if (StringUtils.isAllBlank(incoming.text(), incoming.pictureUrl())) {
                    return null;
                }
                return ChatMessage.textAndPicture(username, incoming.text(), incoming.pictureUrl());
            case PRIVATE:
                if (StringUtils.isBlank(incoming.text()) || StringUtils.isBlank(incoming.to())) {
                    return null;
                }
                return ChatMessage.privateText(username, incoming.to(), incoming.text());

            default:
                return null;
        }
    }
}
