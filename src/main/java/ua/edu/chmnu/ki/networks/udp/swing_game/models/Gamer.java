package ua.edu.chmnu.ki.networks.udp.swing_game.models;

public class Gamer implements java.io.Serializable {
    private Integer id;
    private String name;

    public Gamer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
