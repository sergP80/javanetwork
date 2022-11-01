
package ua.edu.chmnu.ki.networks.tcp.core.server;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractServerApp {
    private static final int DEFAULT_POOL_SIZE = 20;

    private final int poolSize;
    private final ExecutorService executor;

    protected AbstractServerApp(int poolSize) {
        var _poolSize = Integer.parseInt(System.getProperty("server.pool-size", "" + poolSize));
        this.poolSize = (_poolSize > 0) ? _poolSize : DEFAULT_POOL_SIZE;
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public AbstractServerApp() {
        this(DEFAULT_POOL_SIZE);
    }

    public int getPoolSize() {
        return poolSize;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void runApp(String[] args, ClientSessionDelegate delegate) throws IOException {
        Server server = new Server(delegate, executor);
        executor.submit(server);
        System.out.println("To stop application press any key");
        try (Scanner in = new Scanner(System.in)) {
            do {
                String line = in.nextLine();
                if (line.equalsIgnoreCase("Q")) {
                    break;
                }
            } while (true);
        }

        server.setActive(false);
        executor.shutdown();
        System.out.println("Stopped application");
    }
}
