package ua.edu.chmnu.ki.networks.chat.client;


import org.apache.commons.lang3.StringUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClientApp {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 7150;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            ExecutorService executor = Executors.newCachedThreadPool();

            final AtomicBoolean running = new AtomicBoolean(true);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Stopping sender...");
                running.set(false);
                executor.shutdownNow();
            }));

            String host = getEnv("CHAT_HOST", DEFAULT_HOST);
            int port = getEnvInt("CHAT_PORT", DEFAULT_PORT);

            ClientConfig config = new ClientConfig(host, port);

            try (Socket socket = new Socket(config.host(), config.port());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                String userPrompt = reader.readLine();

                String username = null;

                if (StringUtils.isNotBlank(userPrompt)) {
                    System.out.print(userPrompt);
                    username = scanner.nextLine();
                    writer.println(username);
                }

                writer.flush();

                executor.submit(new ServerMessageListener(reader, running));

                Future<?> submit = executor.submit(new ClientConsoleReader(scanner, writer, running, username));

                // To prevent main execution finish
                submit.get();

                running.set(false);

            } catch (IOException e) {
                System.err.println("Client error: " + e.getMessage());
            } catch (ExecutionException | InterruptedException e) {
                System.err.println("Client execution error: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private static String getEnv(String envName, String defaultValue) {
        String value = System.getenv(envName);
        return value != null ? value : defaultValue;
    }

    private static int getEnvInt(String envName, int defaultValue) {
        String value = System.getenv(envName);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid " + envName + ", using default: " + defaultValue);
            }
        }
        return defaultValue;
    }
}
