package ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers;

public class Position implements java.io.Serializable {
    public int x;
    public int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    public static Position zero() {
        return of(0, 0);
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void reset(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
