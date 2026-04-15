package ua.edu.chmnu.ki.networks.chat.client;


import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.core.config.ConfigReader;
import ua.edu.chmnu.ki.networks.core.config.DefaultConfigReader;


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

    private static final String ENV_CHAT_HOST = "CHAT_HOST";
    private static final String ENV_CHAT_PORT = "CHAT_PORT";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            ExecutorService executor = Executors.newCachedThreadPool();

            final AtomicBoolean running = new AtomicBoolean(true);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Stopping sender...");
                running.set(false);
                executor.shutdownNow();
            }));

            ConfigReader configReader = new DefaultConfigReader(scanner);

            ClientConfig config = new ClientConfig(
                    configReader.read(ENV_CHAT_HOST, "localhost"),
                    configReader.readInt(ENV_CHAT_PORT, 7150)
            );

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
}
