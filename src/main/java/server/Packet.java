package server;

import com.google.gson.Gson;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class Packet {
    public final PacketType packetType;
    public final ArrayList<String> data;
    public final Date time = new Date();

    public Packet(PacketType packetType) {
        this.packetType = packetType;
        this.data = new ArrayList<>();
    }

    public Packet(PacketType packetType, ArrayList<String> data) {
        this.packetType = packetType;
        this.data = data;
    }

    public Packet(PacketType packetType, String... data) {
        this.packetType = packetType;
        this.data = new ArrayList<>();
        this.data.addAll(Arrays.asList(data));
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}

