package server;

public enum PacketType {
    SIGNUP, LOGIN, LOGIN_WITH_TOKEN, LOGOUT, GET_LOGGED_IN_USER, FINALIZE_SIGNUP, FIND_USER, SEND_FRIEND_REQUEST,
    ACCEPT_FRIEND, REJECT_FRIEND, CHECK_ONLINE, MAKE_LOBBY, GET_SCOREBOARD, GET_CHATS, MAKE_PRIVATE_CHAT, GET_LOBBY,
    MAKE_GROUP_CHAT, GET_AVAILABLE_LOBBIES, SEND_MESSAGE
}
