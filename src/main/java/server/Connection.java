package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: " + socket.getInetAddress() + " : " + socket.getPort());
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public synchronized void run() {
        try {
            //TODO check if this ip has made an account and auto login.
            String data = dataInputStream.readUTF();
            Packet packet = new Gson().fromJson(data, Packet.class);
            if (packet.command.equals("signup")) {

            } else if (packet.command.equals("login")) {

            }
        } catch (IOException e) {
            System.out.println("Connection " + socket.getInetAddress() + ":" + socket.getPort() + " lost!");
        }
    }
}
