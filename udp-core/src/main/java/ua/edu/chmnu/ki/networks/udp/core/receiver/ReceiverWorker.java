package ua.edu.chmnu.ki.networks.udp.core.receiver;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReceiverWorker<T> implements Runnable {

    private final AtomicBoolean running;
    private final MessageReceiver<T> receiver;
    private final MessageHandler<T> handler;

    public ReceiverWorker(AtomicBoolean running,
                          MessageReceiver<T> receiver,
                          MessageHandler<T> handler) {
        this.running = Objects.requireNonNull(running);
        this.receiver = Objects.requireNonNull(receiver);
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    public void run() {
        while (running.get() && !Thread.currentThread().isInterrupted()) {
            try {
                T message = receiver.receive();

                if (message != null) {
                    System.out.println("Received: " + message);
                    handler.handle(message);
                }
            } catch (Exception e) {
                if (running.get()) {
                    System.err.println("Receiver worker error: " + e.getMessage());
                }
            }
        }
    }
}
