package ua.edu.chmnu.ki.networks.udp.core.sender;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class SenderWorker<T> implements Runnable {

    private final AtomicBoolean running;

    private final long delayMs;

    private final OutgoingMessageProvider<T> provider;

    private final MessageSender<T> sender;

    public SenderWorker(AtomicBoolean running,
                        long delayMs,
                        OutgoingMessageProvider<T> provider,
                        MessageSender<T> sender) {
        this.running = Objects.requireNonNull(running);
        this.delayMs = delayMs;
        this.provider = Objects.requireNonNull(provider);
        this.sender = Objects.requireNonNull(sender);
    }

    @Override
    public void run() {
        T lastMessage = null;

        while (running.get() && !Thread.currentThread().isInterrupted()) {
            try {
                T current = provider.provide();

                if (current != null && !current.equals(lastMessage)) {
                    sender.send(current);
                    System.out.println("Sent: " + current);
                    lastMessage = current;
                }

                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Sender worker interrupted");
            } catch (Exception e) {
                System.err.println("Sender worker error: " + e.getMessage());
            }
        }
    }
}
