package server;

import com.google.gson.Gson;

import java.io.Serializable;

public class Packet implements Serializable {
    String command;

    public String toJson() {
        return new Gson().toJson(this);
    }
}
