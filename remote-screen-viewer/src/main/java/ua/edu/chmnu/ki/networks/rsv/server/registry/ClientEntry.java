package ua.edu.chmnu.ki.networks.rsv.server.registry;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.InetSocketAddress;

public record ClientEntry(
        @JsonProperty("name")
        String name,

        @JsonProperty("address")
        InetSocketAddress address) {
}
