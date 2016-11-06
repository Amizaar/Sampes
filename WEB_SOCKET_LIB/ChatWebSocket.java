package net.company.chatlibrary;


import android.os.Handler;
import android.os.Looper;

import net.company.chatlibrary.chatdata.ChatDataListeners;
import net.company.chatlibrary.connection.ChatConnectionListener;
import net.company.chatlibrary.log.ChatLogListener;
import net.company.chatlibrary.log.ChatLogPresenter;
import net.company.chatlibrary.model.output.AddUser;
import net.company.chatlibrary.model.output.ChangeUsersRoles;
import net.company.chatlibrary.model.output.GetUsers;
import net.company.chatlibrary.model.output.Group;
import net.company.chatlibrary.model.output.Message;
import net.company.chatlibrary.model.output.ReadMessage;
import net.company.chatlibrary.model.output.RestrictUser;
import net.company.chatlibrary.model.output.UnreadSenders;
import net.company.chatlibrary.utils.SSLUtils;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Roman Silka on 9/29/16.
 *
 * Main Class of Chat Library
 * Contains public methods for working with sockets.
 */

public class ChatWebSocket {
    static final String TAG = "ChatWebSocket";

    private static final String GET_BADGES = "get_badges";
    private static final String USER_IS_WRITING = "userIsWriting";

    //output events
    private static final String SEND_MESSAGE = "sendMessage";
    private static final String SET_READED = "setReaded";
    private static final String GET_HISTORY = "getHistory";
    private static final String GET_UNREAD = "getUnread";
    private static final String CREATE_GROUP = "createGroup";
    private static final String CHANGE_GROUP = "changeGroup";
    private static final String ADD_USERS = "addUser";
    private static final String GET_USERS = "getUsers";
    private static final String OPEN_CONVERSATION = "openConversation";
    private static final String CLOSE_CONVERSATION = "closeConversation";
    private static final String SET_USER_STATUS = "setUserStatus";
    private static final String CHANGE_USERS_ROLES = "changeUsersRoles";
    private static final String RESTRICT_USERS = "restrictUsers";
    private static final String DELETE_MESSAGE = "deleteMessage";

    //input events
    private static final String CONNECTED = "connected";
    private static final String MESSAGE_USER = "messageUser";
    private static final String MESSAGE_GROUP = "messageGroup";
    private static final String MESSAGE_BROADCAST = "messageBroadcast";
    private static final String JOINED_TO = "joinedTo";
    private static final String HISTORY = "history";
    private static final String UNREAD = "unread";
    private static final String NEW_GROUP = "newGroup";
    private static final String USER_IN = "userIn";
    private static final String USER_OUT = "userOut";
    private static final String USERS = "users";
    private static final String MESSAGES_ARE_READ = "messagesAreRead";
    private static final String MESSAGES_IS_SENT = "messageIsSent";
    private static final String USER_STATUS = "userStatus";
    private static final String RESTRICTED_RIGHTS = "restrictedRights";
    private static final String NEW_ROLE = "newRole";
    private static final String MESSAGE_IS_DELETED = "messageIsDeleted";
    private static final String SERVER_ERROR = "serverError";

//    private static ChatWebSocket mChatWebSocket;
    private Socket mSocket;
    private ChatWebSocketBuilder mBuilder;
    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private Handler mUserIsWritingHandler = new Handler();

    private ChatLogPresenter mChatLogPresenter;
    private ChatConnectionPresenter mChatConnectionPresenter;
    private ChatDataPresenter mChatDataPresenter;
    private ChatMessageSendPresenter mChatMessageSendPresenter;
    private ChatPresenter mChatPresenter;

    private int userIsWritingRepeatTime = 5000;

    private ChatWebSocket() {

    }

    ChatWebSocket(ChatWebSocketBuilder builder) {
        mBuilder = builder;

        mChatConnectionPresenter = new ChatConnectionPresenter();
        mChatDataPresenter = new ChatDataPresenter();
        mChatLogPresenter = new ChatLogPresenter();
        mChatMessageSendPresenter = new ChatMessageSendPresenter(this);

        mChatPresenter = new ChatPresenter(mChatLogPresenter, mChatConnectionPresenter, mChatDataPresenter, mChatMessageSendPresenter);
    }

    /**
     * Connect to the server.
     *
     * @param authKey auth code from API to connect
     */
    public void connect(String authKey) throws KeyManagementException, NoSuchAlgorithmException {

        if (isConnected()) {
            return;
        }

        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;
        opts.sslContext = SSLUtils.getUnsafeSSL();
        opts.query = "token=" + authKey;

        try {
            mSocket = IO.socket(mBuilder.getChatFrontScheme()+ "://" + mBuilder.getChatFrontPath() + ":" + mBuilder.getPort() + mBuilder.getChatSocketNamespace(), opts);

            mSocket.on(CONNECTED, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onConnected((JSONObject)args[0])
                );
            });

            mSocket.on(MESSAGE_USER, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onMessageUser((JSONObject)args[0])
                );
            });

            mSocket.on(MESSAGE_GROUP, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onMessageGroup((JSONObject)args[0])
                );
            });

            mSocket.on(MESSAGE_BROADCAST, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onMessageBroadcast((JSONObject)args[0])
                );
            });

            mSocket.on(JOINED_TO, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onJoinedTo((JSONObject)args[0])
                );
            });

            mSocket.on(HISTORY, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onHistory((JSONObject)args[0])
                );
            });

            mSocket.on(UNREAD, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onUnread((JSONObject)args[0])
                );
            });

            mSocket.on(NEW_GROUP, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onNewGroup((JSONObject)args[0])
                );
            });

            mSocket.on(USER_IN, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onUserIn((JSONObject)args[0])
                );
            });

            mSocket.on(USER_OUT, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onUserOut((JSONObject)args[0])
                );
            });

            mSocket.on(USERS, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onGroupUsers((JSONObject)args[0])
                );
            });

            mSocket.on(USER_IS_WRITING, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onUserIsWriting((JSONObject)args[0])
                );
            });

            mSocket.on(MESSAGES_IS_SENT, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onMessagesIsSent((JSONObject)args[0])
                );
            });

            mSocket.on(MESSAGES_ARE_READ, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onMessagesAreRead((JSONObject)args[0])
                );
            });

            mSocket.on(USER_STATUS, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onUserStatus((JSONObject)args[0])
                );
            });

            mSocket.on(RESTRICTED_RIGHTS, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onRestrictedRights((JSONObject)args[0])
                );
            });

            mSocket.on(NEW_ROLE, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onChangeUserRoles((JSONObject)args[0])
                );
            });

            mSocket.on(MESSAGE_IS_DELETED, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onMessageIsDeleted((JSONObject)args[0])
                );
            });

            mSocket.on(SERVER_ERROR, (args) -> {
                mMainThreadHandler.post(() ->
                        mChatPresenter.onServerError((JSONObject)args[0])
                );
            });

            //base Socket status callback
            mSocket.on(Socket.EVENT_CONNECT, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onEventConnect(args)
                );
            });

            mSocket.on(Socket.EVENT_DISCONNECT, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onEventDisconnect(args[0])
                );
            });

            mSocket.on(Socket.EVENT_RECONNECT, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onEventReconnect(args[0])
                );
            });

            mSocket.on(Socket.EVENT_CONNECT_ERROR, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onEventConnectError(args[0])
                );
            });

            mSocket.on(Socket.EVENT_ERROR, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onEventError(args[0])
                );
            });

            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, (args) -> {
                mMainThreadHandler.post(() ->
                    mChatPresenter.onEventConnectTimeout(args[0])
                );
            });

            mSocket.connect();

        } catch (URISyntaxException e) {
            mChatLogPresenter.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * Add user`s to room
     *
     * @param groupId is a group id, in which users need to be added.
     * @param userIds List of users
     */
    public void addUserToClosedGroup(String groupId, ArrayList<String> userIds) {
        sendEvent(ADD_USERS, new AddUser(groupId, userIds).toJSONObject());
    }

    /**
     * Change user`s room
     *
     * @param groupId is a group id, in which user need to enter.
     */
    public void changeGroup(String groupId) {
        sendEvent(CHANGE_GROUP, groupId);
    }

    /**
     * Create private user`s room
     *
     * @param name Name of a group
     * @param userIds List of users
     * @param needBadges flag for unread messages
     */
    public void createPrivateGroup(String name, ArrayList<String> userIds, boolean needBadges) {
        createGroup(new Group(name, userIds, needBadges, Group.GROUP_CLOSED_TYPE));
    }

    /**
     * Create public user`s room
     *
     * @param name Name of a group
     */
    public void createPublicGroup(String name) {
        createGroup(new Group(name, null, false, Group.GROUP_OPEN_TYPE));
    }

    /**
     * Create user`s room
     */
    public void createGroup(Group group) {
        sendEvent(CREATE_GROUP, group.toJsonObject());
    }

    /**
     * Get user`s history
     *
     * @param type type of user`s history. Server required 'user' or 'group'
     * @param typeId id of the user or of the group
     */
    public void getHistory(String type, String typeId) {
        sendEvent(GET_HISTORY, type, typeId);
    }

    /**
     * Get user`s history
     *
     * @param type type of user`s history. Server required 'user' or 'group'
     * @param typeId id of the user or of the group
     * @param from date from which required history
     */
    public void getHistory(String type, String typeId, long from) {
        sendEvent(GET_HISTORY, type, typeId, from);
    }

    /**
     * Get user`s history
     *
     * @param type type of user`s history. Server required 'user' or 'group'
     * @param typeId id of the user or of the group
     * @param from date from which required history
     * @param byTime date to which required history
     */
    public void getHistory(String type, String typeId, long from, long byTime) {
        sendEvent(GET_HISTORY, type, typeId, from, byTime);
    }

    /**
     * Send user`s message
     *
     * @param message message to send
     */
    public void sendMessage(Message message) {
        mChatMessageSendPresenter.sendMessage(message);
    }

    void sendMessageEvent(Message message) {
        sendEvent(SEND_MESSAGE, message.toJsonObject());
    }

    /**
     * Get unread messages
     *
     * @param ids id`s of users, that required unread messages
     */
    public void getUnread(String[] ids) {
        getUnread(new ArrayList<>(Arrays.asList(ids)));
    }

    /**
     * Get unread messages
     *
     * @param ids list of user`s id, that required unread messages
     */
    public void getUnread(ArrayList<String> ids) {
        sendEvent(GET_UNREAD, new UnreadSenders(ids).toJson());
    }

    /**
     * Read message (deprecated)
     *
     * @param messageId id of the message that required set there flag to read.
     */
//    public void setReaded(String messageId) {
//        sendEvent(SET_READED, messageId);
//    }

    /**
     * Read message
     *
     * @param messageId id of the message that required set there flag to read.
     */
    public void setReaded(String senderId, String messageId) {
        ArrayList<String> messageIds = new ArrayList<>();
        messageIds.add(messageId);
        sendEvent(SET_READED, new ReadMessage(senderId, messageIds).toJSONObject());
    }

    /**
     * Read message
     *
     * @param messageIds id of the messages that required set there flag to read.
     */
    public void setReaded(String senderId, ArrayList<String> messageIds) {
        sendEvent(SET_READED, new ReadMessage(senderId, messageIds).toJSONObject());
    }

    /**
     * Read message
     *
     * @param senderId id of the user which chat required set there flag to read.
     */
    public void setReadedAll(String senderId) {
        sendEvent(SET_READED, new ReadMessage(senderId).toJSONObject());
    }

    /**
     * Get all user`s in the server
     *
     */
    public void getAllUsers() {
        sendEvent(GET_USERS, new GetUsers().toJson());
    }

    /**
     * Get user`s out of group
     *
     * @param groupId
     */
    public void getNotInGroupUsers(String groupId) {
        sendEvent(GET_USERS, new GetUsers(groupId).toJson());
    }

    /**
     * Send user writing status to recipient
     *
     * @param recipientId
     */
    public void setUserIsWritingStatus(String recipientId) {
        if (!mUserIsWritingHandler.hasMessages(0)) {
            sendEvent(USER_IS_WRITING, recipientId);
            mUserIsWritingHandler.postDelayed(() ->
                    sendEvent(USER_IS_WRITING, recipientId), userIsWritingRepeatTime);
        }
    }

    /**
     * Open conversation
     *
     * @param companionId
     */
    public void openConversation(String companionId) {
        sendEvent(OPEN_CONVERSATION, companionId);
    }

    /**
     * Close conversation
     *
     * @param companionId
     */
    public void closeConversation(String companionId) {
        sendEvent(CLOSE_CONVERSATION, companionId);
    }

    /**
     * Set user status
     *
     * @param status
     */
    public void setUserStatus(String status) {
        sendEvent(SET_USER_STATUS, status);
    }

    /**
     * Ban selected user
     *
     * @param groupId
     * @param usersId id of user, that need to be banned
     * @param period final date of userBar in millisec. If < 0 - than forever. 0 - if need remove a ban
     */
    public void banUser(String groupId, String usersId, long period) {
        ArrayList<String> usersIds = new ArrayList<>();
        usersIds.add(usersId);
        restrictUsers(groupId, usersIds, period);
    }

    /**
     * Ban selected users
     *
     * @param groupId
     * @param usersIds id of users, that need to be banned
     * @param period final date of userBar in millisec. If < 0 - than forever. 0 - if need remove a ban
     */
    public void banUsers(String groupId, ArrayList<String> usersIds, long period) {
        restrictUsers(groupId, usersIds, period);
    }

    /**
     * Unban selected user
     *
     * @param groupId
     * @param usersId id of user, that need to be removed a ban
     */
    public void unbanUser(String groupId, String usersId) {
        ArrayList<String> usersIds = new ArrayList<>();
        usersIds.add(usersId);
        restrictUsers(groupId, usersIds, 0);
    }

    /**
     * Unban selected users
     *
     * @param groupId
     * @param usersIds id of users, that need to be removed a ban
     */
    public void unbanUsers(String groupId, ArrayList<String> usersIds) {
        restrictUsers(groupId, usersIds, 0);
    }

    /**
     * Ban/unban selected users
     *
     * @param groupId
     * @param usersIds id of users, that need to be banned
     * @param period final date of userBar in millisec. If < 0 - than forever. 0 - if need remove a ban
     */
    private void restrictUsers(String groupId, ArrayList<String> usersIds, long period) {
        sendEvent(RESTRICT_USERS, new RestrictUser(groupId, usersIds, period).toJson());
    }

    /**
     * Change user role
     *
     * @param groupId
     * @param usersId id of user, that role need to be changed
     * @param role standart role from @UserRoles.class
     */
    public void changeUserRole(String groupId, String usersId, String role) {
        ArrayList<String> usersIds = new ArrayList<>();
        usersIds.add(usersId);
        sendEvent(CHANGE_USERS_ROLES, new ChangeUsersRoles(groupId, usersIds, role).toJson());
    }

    /**
     * Change user role
     *
     * @param groupId
     * @param usersIds id of users, that roles need to be changed
     * @param role standart role from @UserRoles.class
     */
    public void changeUsersRole(String groupId, ArrayList<String> usersIds, String role) {
        sendEvent(CHANGE_USERS_ROLES, new ChangeUsersRoles(groupId, usersIds, role).toJson());
    }

    /**
     * Delete message by id
     */
    public void deleteMessage(String messageId) {
        sendEvent(DELETE_MESSAGE, messageId);
    }

    /**
     * Check connection status
     *
     * @return connection status
     */
    public boolean isConnected() {
        return mSocket != null && mSocket.connected();
    }

    /**
     * Emits an event is connected and show event log.
     *
     * @param event an event name.
     * @param args data to send.
     */
    private void sendEvent(String event, Object... args) {
        mChatLogPresenter.i(TAG, event + " / " + (args != null ? args[0].toString() : "null"));
        if (isConnected())
            mSocket.emit(event, args);
    }

    /**
     * Disconnect from the server.
     */
    public void disconnect() {
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off();
            mSocket = null;
        }
    }

    /**
     * Set connection listener
     *
     * @param listener SocketConnectionListener
     */
    public void setSocketConnectionListener(ChatConnectionListener.SocketConnectionListener listener) {
        mChatConnectionPresenter.setSocketConnectionListener(listener);
    }

    /**
     * Remove connection listener
     */
    public void removeSocketConnectionListener() {
        mChatConnectionPresenter.removeSocketConnectionListener();
    }

    /**
     * Add data listener
     *
     * @param chatDataListener ChatDataListener
     */
    public void addChatDataListener(ChatDataListeners.ChatDataListener chatDataListener) {
        mChatDataPresenter.addChatStatusListener(chatDataListener);
    }

    /**
     * Remove data listener
     * *
     * @param chatDataListener ChatDataListener
     */
    public void removeChatDataListener(ChatDataListeners.ChatDataListener chatDataListener) {
        mChatDataPresenter.removeChatStatusListener(chatDataListener);
    }

    /**
     * Add log listener
     *
     * @param chatLogListener ChatDataListener
     */
    public void setLogListener(ChatLogListener chatLogListener) {
        mChatLogPresenter.setChatLogListener(chatLogListener);
    }

    /**
     * Remove log listener
     */
    public void removeLogListener() {
        mChatLogPresenter.removeLogListener();
    }

    public void setUserIsWritingRepeatTime(int milliseconds) {
        userIsWritingRepeatTime = milliseconds;
    }
}
