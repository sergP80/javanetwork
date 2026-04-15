package ua.edu.chmnu.ki.networks.chat.server;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRegistry {

    private final Map<String, ChatClientSession> sessions = new ConcurrentHashMap<>();

    public boolean register(ChatClientSession session) {
        return sessions.putIfAbsent(session.getUsername(), session) == null;
    }

    public void unregister(String username) {
        if (username != null) {
            sessions.remove(username);
        }
    }

    public boolean exists(String username) {
        return sessions.containsKey(username);
    }

    public Collection<ChatClientSession> all() {
        return sessions.values();
    }

    public ChatClientSession get(String username) {
        return sessions.get(username);
    }
}
