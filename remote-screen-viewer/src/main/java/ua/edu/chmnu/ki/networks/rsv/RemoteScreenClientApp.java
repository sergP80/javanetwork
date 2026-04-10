package ua.edu.chmnu.ki.networks.rsv;

import ua.edu.chmnu.ki.networks.rsv.client.ScreenClient;
import ua.edu.chmnu.ki.networks.rsv.client.ScreenClientImpl;

public class RemoteScreenClientApp {

    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromEnvForClient();
        ScreenClient client = new ScreenClientImpl(config);
        client.start();
    }
}
