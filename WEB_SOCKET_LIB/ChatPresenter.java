package net.company.chatlibrary;

import com.google.gson.Gson;

import net.company.chatlibrary.log.ChatLogPresenter;
import net.company.chatlibrary.model.input.*;
import net.company.chatlibrary.model.input.errors.*;
import net.company.chatlibrary.model.input.status.*;

import org.json.JSONObject;

/**
 * Created by Roman Silka on 10/12/16.
 */

class ChatPresenter {
    private Gson mGson = new Gson();

    private ChatLogPresenter mChatLogPresenter;
    private ChatConnectionPresenter mChatConnectionPresenter;
    private ChatDataPresenter mChatDataPresenter;
    private ChatMessageSendPresenter mChatMessageSendPresenter;

    ChatPresenter(ChatLogPresenter chatLogPresenter,
                  ChatConnectionPresenter chatConnectionPresenter,
                  ChatDataPresenter chatDataPresenter,
                  ChatMessageSendPresenter chatMessageSendPresenter) {
        mChatLogPresenter = chatLogPresenter;
        mChatConnectionPresenter = chatConnectionPresenter;
        mChatDataPresenter = chatDataPresenter;
        mChatMessageSendPresenter = chatMessageSendPresenter;
    }

    void onConnected(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_CONNECTED " + jsonObject);
        mChatDataPresenter.onConnected(mGson.fromJson(jsonObject.toString(), UserData.class));
    }

    void onMessageUser(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_MESSAGE_USER" + jsonObject);
        mChatDataPresenter.onMessageUserReceived(observeMessage(jsonObject));
    }

    void onMessageGroup(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_MESSAGE_GROUP" + jsonObject);
        mChatDataPresenter.onMessageGroupReceived(observeMessage(jsonObject));
    }

    void onMessageBroadcast(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_MESSAGE_BROADCAST" + jsonObject);
        mChatDataPresenter.onMessageBroadcastReceived(observeMessage(jsonObject));
    }

    private Message observeMessage(JSONObject jsonObject) {
        Message inputMessage = mGson.fromJson(jsonObject.toString(), net.company.chatlibrary.model.input.Message.class);
        mChatMessageSendPresenter.onNewInputMessage(inputMessage);
        return inputMessage;
    }

    void onJoinedTo(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_JOINED_TO " + jsonObject);
        mChatDataPresenter.onJoinedTo(mGson.fromJson(jsonObject.toString(), JoinedGroup.class));
    }

    void onHistory(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_HISTORY" + jsonObject);
        mChatDataPresenter.onHistoryData(mGson.fromJson(jsonObject.toString(), History.class));
    }

    void onUnread(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_UNREAD" + jsonObject);
        mChatDataPresenter.onUnreadMessages(mGson.fromJson(jsonObject.toString(), UnreadMessages.class));
    }

    void onNewGroup(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_NEW_GROUP" + jsonObject);
        mChatDataPresenter.onNewGroup(mGson.fromJson(jsonObject.toString(), net.company.chatlibrary.model.input.Group.class));
    }

    void onUserIn(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_USER_IN " + jsonObject);
        mChatDataPresenter.onUserIn(mGson.fromJson(jsonObject.toString(), UserConnectionIn.class));
    }

    void onUserOut(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_USER_OUT" + jsonObject);
        mChatDataPresenter.onUserOut(mGson.fromJson(jsonObject.toString(), UserConnectionOut.class));
    }

    void onGroupUsers(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_NOT_IN_GROUP_USERS" + jsonObject);
        mChatDataPresenter.onGroupUsers(mGson.fromJson(jsonObject.toString(), GroupUsers.class));
    }

    void onUserIsWriting(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ON_USER_IS_WRITING" + jsonObject);
        mChatDataPresenter.onUserIsWriting(mGson.fromJson(jsonObject.toString(), User.class));
    }

    void onMessagesIsSent(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ON_MESSAGES_IS_SENT" + jsonObject);
        mChatDataPresenter.onMessagesIsSent(mGson.fromJson(jsonObject.toString(), SendStatus.class));
    }

    void onMessagesAreRead(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ON_MESSAGES_ARE_READ" + jsonObject);
        mChatDataPresenter.onMessagesAreRead(mGson.fromJson(jsonObject.toString(), ReadStatus.class));
    }

    void onUserStatus(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ON_USER_STATUS" + jsonObject);
        mChatDataPresenter.onUserStatus(mGson.fromJson(jsonObject.toString(), UserStatus.class));
    }

    void onChangeUserRoles(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ON_CHANGE_USER_ROLES" + jsonObject);
        mChatDataPresenter.onChangeUserRoles(mGson.fromJson(jsonObject.toString(), NewRole.class));
    }

    void onRestrictedRights(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ON_RESTRICTED_RIGHTS" + jsonObject);
        mChatDataPresenter.onRestrictedRights(mGson.fromJson(jsonObject.toString(), RestrictedRights.class));
    }

    void onMessageIsDeleted(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ON_MESSAGE_IS_DELETED" + jsonObject);
        mChatDataPresenter.onMessageIsDeleted(mGson.fromJson(jsonObject.toString(), MessageIsDeleted.class));
    }

    void onServerError(JSONObject jsonObject) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_SERVER_ERROR" + jsonObject);
        ChatServerError chatServerError;
        if (jsonObject.has(ChatServerError.ACTION_KEY)){
            String action = jsonObject.optString(ChatServerError.ACTION_KEY);

            switch (action) {
                case ChatServerError.CREATE_GROUP_ERROR:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatCreateGroupError.class);
                    break;
                case ChatServerError.CHANGE_GROUP_ERROR:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatChangeGroupError.class);
                    break;
                case ChatServerError.SET_USER_STATUS_ERROR:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatSetUserStatusError.class);
                    break;
                case ChatServerError.ADD_USER_ERROR:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatAddUserError.class);
                    break;
                case ChatServerError.USER_IS_WRITING:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatUserIsWritingError.class);
                    break;
                case ChatServerError.SEND_MESSAGE:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatSendMessageError.class);
                    break;
                case ChatServerError.GET_HISTORY:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatGetHistoryError.class);
                    break;
                case ChatServerError.GET_USERS:
                case ChatServerError.GET_USERS_NOT_IN_GROUP:
                case ChatServerError.GET_USERS_ALL_REGISTERED:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatGetUsersError.class);
                    break;
                case ChatServerError.SEND_TO_USER:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatSendToUserError.class);
                    break;
                case ChatServerError.SEND_TO_GROUP:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatSendToGroupError.class);
                    break;
                case ChatServerError.SEND_TO_ALL:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatSendToAllError.class);
                    break;
                case ChatServerError.SET_READED:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatSetReadedError.class);
                    break;
                case ChatServerError.CHECK_UNREAD:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatCheckUnreadError.class);
                    break;
                case ChatServerError.CHANGE_USERS_ROLES:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatChangeUsersRolesError.class);
                    break;
                case ChatServerError.RESTRICT_USERS:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatRestrictUsersError.class);
                    break;
                case ChatServerError.DELETE_MESSAGE:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatDeleteMessageError.class);
                    break;

                default:
                    chatServerError = mGson.fromJson(jsonObject.toString(), ChatUndefinedError.class);
                    break;
            }
        } else
            chatServerError = mGson.fromJson(jsonObject.toString(), ChatServerUnknownError.class);

        mChatDataPresenter.onError(chatServerError);
    }

    //base Socket status callback
    void onEventConnect(Object... object) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "Connect");
        if (object != null && object.length > 0)
            mChatConnectionPresenter.onConnect(object);

        mChatMessageSendPresenter.updateUnreadMessagesList();
        if (mChatMessageSendPresenter.getUnsendedMessages().size() > 0)
            mChatMessageSendPresenter.sendAllUnsendedMessages();
    }

    void onEventDisconnect(Object object) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_DISCONNECT " + object);
        mChatConnectionPresenter.onDisconnect(object);
    }

    void onEventReconnect(Object object) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_RECONNECT " + object);
        mChatConnectionPresenter.onReconnect(object);
    }

    void onEventConnectError(Object object) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_CONNECT_ERROR");
        mChatConnectionPresenter.onConnectionError(object);
    }

    void onEventError(Object object) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_ERROR");
        mChatConnectionPresenter.onError(object);
    }

    void onEventConnectTimeout(Object object) {
        mChatLogPresenter.i(ChatWebSocket.TAG, "EVENT_CONNECT_TIMEOUT");
        mChatConnectionPresenter.onEventConnectTimeout(object);
    }
}
