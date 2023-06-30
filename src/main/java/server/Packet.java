package server;

import com.google.gson.Gson;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Packet implements Serializable {
    public enum PacketType {
        SIGNUP, LOGIN, LOGOUT,
    }

    final PacketType PacketType;
    final String data;
    final LocalDateTime time;

    public Packet(Packet.PacketType packetType, String data) {
        PacketType = packetType;
        this.data = data;
        time = LocalDateTime.now();
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
