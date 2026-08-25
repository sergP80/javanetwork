package ua.edu.chmnu.ki.networks.chat.server;

import ua.edu.chmnu.ki.networks.chat.server.session.ChatClientSession;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRegistry {

    private final Map<String, ChatClientSession> sessions = new ConcurrentHashMap<>();

    public void register(ChatClientSession session) {
        sessions.putIfAbsent(session.getUsername(), session);
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
