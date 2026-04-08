
package ua.edu.chmnu.ki.networks.udp.core;

import lombok.Getter;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Getter
public abstract class AbstractReceiverApp {

    private final ExecutorService executor;

    protected AbstractReceiverApp() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public <T> void runApp(String[] args, Consumer<T> consumer) throws IOException {
        int port;

        if (args == null || args.length == 0) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter port:");
                port = scanner.nextInt();
            }
        } else {
            port = Integer.parseInt(args[0]);
        }


        BasicReceiver<T> receiver = new BasicReceiver<>(port, consumer);

        executor.submit(receiver);

        System.out.println("To stop application press Q");
        try (Scanner in = new Scanner(System.in)) {
            do {

                if (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.equalsIgnoreCase("Q")) {
                        receiver.setActive(false);
                        break;
                    }
                }

                Thread.sleep(1000);
            } while (true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        receiver.setActive(false);
        executor.shutdown();
        System.out.println("Stopped application");
    }
}
