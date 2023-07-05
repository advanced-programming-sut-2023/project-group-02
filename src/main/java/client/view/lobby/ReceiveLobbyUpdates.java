package client.view.lobby;

import client.view.Main;
import server.logic.Lobby;

public class ReceiveLobbyUpdates extends Thread {
    private final LobbyMenu lobbyMenu;
    private final String lobbyID;

    public ReceiveLobbyUpdates(LobbyMenu lobbyMenu, String lobbyID) {
        this.lobbyMenu = lobbyMenu;
        this.lobbyID = lobbyID;
    }

    @Override
    public synchronized void run() {
        try {
            wait(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Lobby lobby = Main.getPlayerConnection().getLobbyWithID(lobbyID);
        lobbyMenu.setLobby(lobby);
        System.out.println("updated");
    }
}
