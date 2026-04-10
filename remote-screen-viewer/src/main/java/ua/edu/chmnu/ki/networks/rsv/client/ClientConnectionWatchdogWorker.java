package ua.edu.chmnu.ki.networks.rsv.client;

public class ClientConnectionWatchdogWorker implements Runnable {

    private final ConnectionMonitor connectionMonitor;
    private final Runnable disconnectAction;
    private final long checkIntervalMillis;

    public ClientConnectionWatchdogWorker(ConnectionMonitor connectionMonitor,
                                          Runnable disconnectAction,
                                          long checkIntervalMillis) {
        this.connectionMonitor = connectionMonitor;
        this.disconnectAction = disconnectAction;
        this.checkIntervalMillis = checkIntervalMillis;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (!connectionMonitor.isAlive()) {
                    disconnectAction.run();
                    return;
                }

                Thread.sleep(checkIntervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
