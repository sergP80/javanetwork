package ua.edu.chmnu.ki.networks.chat.server;


import ua.edu.chmnu.ki.networks.core.config.ConfigReader;
import ua.edu.chmnu.ki.networks.core.config.DefaultConfigReader;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatServerApp {

    private static final String ENV_SERVER_PORT = "CHAT_SERVER_PORT";
    private static final String ENV_SERVER_THREADS = "CHAT_SERVER_THREADS";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            ConfigReader configReader = new DefaultConfigReader(scanner);

            ServerConfig config = new ServerConfig(
                    configReader.readInt(ENV_SERVER_PORT, 7150),
                    configReader.readInt(ENV_SERVER_THREADS, 10)
            );
            new ChatServer(config).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
