
package ua.edu.chmnu.ki.networks.udp.core;

import lombok.Getter;
import ua.edu.chmnu.ki.networks.udp.core.sender.MessageSender;
import ua.edu.chmnu.ki.networks.udp.core.sender.OutgoingMessageProvider;
import ua.edu.chmnu.ki.networks.udp.core.sender.SenderWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SenderApp<T> {

    private static final long DEFAULT_DELAY_SEND = 50L;

    private final ExecutorService executor;

    @Getter
    private final AtomicBoolean running;

    @Getter
    private final MessageSender<T> messageSender;

    @Getter
    private final OutgoingMessageProvider<T> outgoingMessageProvider;

    public SenderApp(MessageSender<T> messageSender, OutgoingMessageProvider<T> outgoingMessageProvider) {
        this.messageSender = messageSender;
        this.outgoingMessageProvider = outgoingMessageProvider;
        this.executor = Executors.newSingleThreadExecutor();
        this.running = new AtomicBoolean(true);
    }

    public void runApp(String[] args) {

        long delayMs = Long.parseLong(System.getenv().getOrDefault("UDP_SEND_DELAY", "" + DEFAULT_DELAY_SEND));

        var worker = new SenderWorker<>(running, delayMs, outgoingMessageProvider, messageSender);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping sender...");
            running.set(false);
            executor.shutdownNow();
            messageSender.close();
        }));

        executor.submit(worker);
    }
}
