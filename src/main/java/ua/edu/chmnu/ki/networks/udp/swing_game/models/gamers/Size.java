package ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers;

public class Size implements java.io.Serializable {
    public int width;
    public int height;

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size of (int w, int h) {
        return new Size(w, h);
    }

    public static Size zero() {
        return of(0, 0);
    }
    public void scale(float scaleFactor) {
        this.width = (int)(this.width * scaleFactor);
        this.height = (int)(this.height * scaleFactor);
    }
}
