package ua.edu.chmnu.ki.networks.rsv.client;


import java.util.concurrent.atomic.AtomicLong;

public class HeartbeatConnectionMonitor implements ConnectionMonitor {

    private final AtomicLong lastSeenAt;

    private final long timeoutMillis;

    public HeartbeatConnectionMonitor(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        this.lastSeenAt = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public void onHeartbeat() {
        lastSeenAt.set(System.currentTimeMillis());
    }

    @Override
    public void onFrameReceived() {
        lastSeenAt.set(System.currentTimeMillis());
    }

    @Override
    public boolean isAlive() {
        long now = System.currentTimeMillis();
        return now - lastSeenAt.get() <= timeoutMillis;
    }
}
