package ua.edu.chmnu.ki.networks.rsv.server;


import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.server.registry.ClientRegistryService;
import ua.edu.chmnu.ki.networks.rsv.server.service.ScreenBroadcastService;

@AllArgsConstructor
public class FrameBroadcastWorker implements Runnable {

    private final ClientRegistryService clientRegistryService;
    private final ScreenBroadcastService screenBroadcastService;
    private final int fps;

    @Override
    public void run() {
        long delayMs = Math.max(1, 1000L / Math.max(1, fps));

        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (clientRegistryService.isEmpty()) {
                    Thread.sleep(500);
                    continue;
                }

                screenBroadcastService.captureAndSend();

                Thread.sleep(delayMs);
            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.err.println("Broadcast error: " + e.getMessage());
                }
            }
        }
    }
}
