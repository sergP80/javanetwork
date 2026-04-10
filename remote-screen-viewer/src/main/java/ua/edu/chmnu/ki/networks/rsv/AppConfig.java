package ua.edu.chmnu.ki.networks.rsv;

public record AppConfig(
        String host,
        int port,
        int fps,
        float jpegQuality,
        int packetSize
) {
    public static AppConfig fromEnvForServer() {
        return new AppConfig(
                System.getenv().getOrDefault("UDP_HOST", "0.0.0.0"),
                Integer.parseInt(System.getenv().getOrDefault("UDP_PORT", "5000")),
                Integer.parseInt(System.getenv().getOrDefault("UDP_FPS", "8")),
                Float.parseFloat(System.getenv().getOrDefault("UDP_JPEG_QUALITY", "0.5")),
                Integer.parseInt(System.getenv().getOrDefault("UDP_PACKET_SIZE", "60000"))
        );
    }

    public static AppConfig fromEnvForClient() {
        return new AppConfig(
                System.getenv().getOrDefault("SERVER_HOST", "127.0.0.1"),
                Integer.parseInt(System.getenv().getOrDefault("SERVER_PORT", "5000")),
                0,
                0.0f,
                Integer.parseInt(System.getenv().getOrDefault("UDP_PACKET_SIZE", "60000"))
        );
    }
}
