package ua.edu.chmnu.ki.networks.rsv.client.worker;

import ua.edu.chmnu.ki.networks.rsv.client.gui.ClientWindow;
import ua.edu.chmnu.ki.networks.rsv.client.monitor.ConnectionMonitor;

public class ClientConnectionWatchdogWorker implements Runnable {

    private final ConnectionMonitor connectionMonitor;
    private final ClientWindow clientWindow;
    private final long checkIntervalMillis;

    public ClientConnectionWatchdogWorker(ConnectionMonitor connectionMonitor,
                                          ClientWindow clientWindow,
                                          long checkIntervalMillis) {
        this.connectionMonitor = connectionMonitor;
        this.clientWindow = clientWindow;
        this.checkIntervalMillis = checkIntervalMillis;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                boolean isAlive = connectionMonitor.isAlive();

                clientWindow.updateHeartbeatStatus(isAlive);

                Thread.sleep(checkIntervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
