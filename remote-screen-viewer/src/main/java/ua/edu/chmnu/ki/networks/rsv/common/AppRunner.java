package ua.edu.chmnu.ki.networks.rsv.common;

import java.io.IOException;

public interface AppRunner {
    void start() throws Exception;

    void stop();

    default void restart() throws IOException {
    }
}
