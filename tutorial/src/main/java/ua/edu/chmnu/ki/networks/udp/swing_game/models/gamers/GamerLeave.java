package ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers;

import java.io.Serializable;
import java.util.UUID;

public record GamerLeave(UUID gamerId) implements Serializable {
}
