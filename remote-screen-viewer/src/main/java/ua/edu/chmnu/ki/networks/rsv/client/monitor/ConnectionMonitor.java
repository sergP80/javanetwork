package ua.edu.chmnu.ki.networks.rsv.client.monitor;

public interface ConnectionMonitor {
    void onHeartbeat();
    void onFrameReceived();
    boolean isAlive();
}
