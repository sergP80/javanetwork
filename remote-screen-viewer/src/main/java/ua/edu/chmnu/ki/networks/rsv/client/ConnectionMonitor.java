package ua.edu.chmnu.ki.networks.rsv.client;

public interface ConnectionMonitor {
    void onHeartbeat();
    void onFrameReceived();
    boolean isAlive();
}
