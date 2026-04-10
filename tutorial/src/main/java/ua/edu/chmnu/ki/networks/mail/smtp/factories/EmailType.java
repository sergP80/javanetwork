package ua.edu.chmnu.ki.networks.mail.smtp.factories;

public enum EmailType {
    TEXT, HTML, HTML_MULTI_PART;

    public static EmailType of(int index) {
        for (int i = 0; i < values().length; ++i) {
            if (i == index) {
                return values()[i];
            }
        }
        throw new IllegalArgumentException("Incorrect index of email type");
    }
}
