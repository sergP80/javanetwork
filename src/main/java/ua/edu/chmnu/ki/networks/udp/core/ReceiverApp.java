
package ua.edu.chmnu.ki.networks.udp.core;

import lombok.Getter;
import ua.edu.chmnu.ki.networks.udp.core.receiver.MessageHandler;
import ua.edu.chmnu.ki.networks.udp.core.receiver.MessageReceiver;
import ua.edu.chmnu.ki.networks.udp.core.receiver.ReceiverWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReceiverApp<T> {

    private final ExecutorService executor;

    @Getter
    private final AtomicBoolean running;

    @Getter
    private final MessageReceiver<T> messageReceiver;

    @Getter
    private final MessageHandler<T> messageHandler;

    public ReceiverApp(MessageReceiver<T> messageReceiver, MessageHandler<T> messageHandler) {
        this.messageReceiver = messageReceiver;
        this.messageHandler = messageHandler;
        this.executor = Executors.newSingleThreadExecutor();
        this.running = new AtomicBoolean(true);
    }

    public void runApp(String[] args) {

        var worker = new ReceiverWorker<>(running, messageReceiver, messageHandler);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping sender...");
            running.set(false);
            executor.shutdownNow();
            messageReceiver.close();
        }));

        executor.submit(worker);
    }
}
