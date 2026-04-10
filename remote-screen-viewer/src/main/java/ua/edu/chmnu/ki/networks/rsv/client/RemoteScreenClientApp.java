package ua.edu.chmnu.ki.networks.rsv.client;

import ua.edu.chmnu.ki.networks.rsv.AppConfig;

public class RemoteScreenClientApp {

    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromEnvForClient();
        ScreenClient client = new ScreenClientImpl(config);
        client.start();
    }
}
