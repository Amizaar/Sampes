package net.company.chatlibrary;

/**
 * Created by Roman Silka on 9/30/16.
 *
 * The builder class for Chat Library
 * Contains methods for create and config ChatWebSocket class
 */

public class ChatWebSocketBuilder {
    private int mPort;
    private String mChatFrontPath;
    private String mChatFrontScheme;
    private String mChatSocketNamespace;

    public ChatWebSocketBuilder() {

    }

    public ChatWebSocketBuilder setPort(int port) {
        mPort = port;
        return this;
    }

    public ChatWebSocketBuilder setChatFrontScheme(String ChatFrontScheme) {
        mChatFrontScheme = ChatFrontScheme;
        return this;
    }

    public ChatWebSocketBuilder setChatFrontPath(String chatFrontPath) {
        mChatFrontPath = chatFrontPath;
        return this;
    }

    public ChatWebSocketBuilder setChatSocketNamespace(String chatSocketNamespace) {
        mChatSocketNamespace = chatSocketNamespace;
        return this;
    }

    public int getPort() {
        return mPort;
    }

    public String getChatFrontPath() {
        return mChatFrontPath;
    }

    public String getChatFrontScheme() {
        return mChatFrontScheme;
    }

    public String getChatSocketNamespace() {
        return mChatSocketNamespace;
    }

    public ChatWebSocket build() {
        return new ChatWebSocket(this);
    }
}
