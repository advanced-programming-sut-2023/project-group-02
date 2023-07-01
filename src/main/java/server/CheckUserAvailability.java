package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CheckUserAvailability extends Thread {
    private final DataOutputStream dataOutputStream;
    private final Connection connection;
    private boolean isOnline = true;

    public CheckUserAvailability(DataOutputStream dataOutputStream, Connection connection) {
        this.dataOutputStream = dataOutputStream;
        this.connection = connection;
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                dataOutputStream.writeUTF(new Packet((PacketType) null, (ArrayList<String>) null).toJson());
                wait(2000);
            } catch (IOException e) {
                userDisconnected();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    protected synchronized void userDisconnected() {
        if (!isOnline)
            return;
        isOnline = false;
        connection.userDisconnected();
    }
}
