package server;

import com.google.gson.Gson;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Packet implements Serializable {

    public final PacketType PacketType;
    public final ArrayList<String> data;
    public final LocalDateTime time;

    public Packet(PacketType packetType, ArrayList<String> data) {
        this.PacketType = packetType;
        this.data = data;
        this.time = LocalDateTime.now();
    }

    public Packet(PacketType packetType, String... data) {
        PacketType = packetType;
        this.data = new ArrayList<>();
        this.data.addAll(Arrays.asList(data));
        this.time = LocalDateTime.now();
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}

