package com.project.server.sevice;


import com.project.common.Message;
import com.project.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 管理通信线程类
 */

public class ManageConnectThread {

    private static HashMap<String, ServerConnectClientThread> serverConnectThreadHashMap = new HashMap<>();

    public static void addToServerThreadHashMap(String userID, ServerConnectClientThread serverThread) {
        serverConnectThreadHashMap.put(userID, serverThread);
    }

    public static ServerConnectClientThread getClientThread(String userName) {
        return serverConnectThreadHashMap.get(userName);
    }

    public static void removeClientThread(String userName) {
        serverConnectThreadHashMap.remove(userName);
    }

    public static String getOnlineUsers() {
        StringBuffer onlineUserInfo = new StringBuffer();
        Set<Map.Entry<String, ServerConnectClientThread>> usersInfo = serverConnectThreadHashMap.entrySet();
        for (Map.Entry<String, ServerConnectClientThread> user : usersInfo) {
            onlineUserInfo.append(user.getKey()).append(" ");
        }
        onlineUserInfo.delete(onlineUserInfo.length() - 1, onlineUserInfo.length());

        return onlineUserInfo.toString();
    }

    public static void exitUser(String sender) throws IOException {
        ServerConnectClientThread clientThread = serverConnectThreadHashMap.get(sender);

        // 写这一行是因为客户端主线程虽然直接exit(0)了，但那个socket的线程还没退出，会导致Java.IO.EOFEXCEPTION
        Message message = new Message();
        message.setType(MessageType.MESSAGE_CLIENT_EXIT);
        ObjectOutputStream objOutput = new ObjectOutputStream(clientThread.getSocket().getOutputStream());
        objOutput.writeObject(message);
        objOutput.flush();

        clientThread.getSocket().close();
        clientThread.exitThread();

        serverConnectThreadHashMap.remove(sender);
    }

    public static void transferMessage(Message message) {

        String receiver = message.getReceiver();
        ServerConnectClientThread getter = serverConnectThreadHashMap.get(receiver);
        getter.sendToTarget(message, MessageType.MESSAGE_SEND_TO_CLIENT);
    }

    public static void transferMessageToAll(Message message) {

        for (Map.Entry<String, ServerConnectClientThread> user : serverConnectThreadHashMap.entrySet()) {
            if (!user.getKey().equals(message.getSender())) {
                ServerConnectClientThread clientThread = user.getValue();
                clientThread.sendToTarget(message, MessageType.MESSAGE_SEND_ALL_TO_CLIENT);
            }
        }
    }

    public static void transferFile(Message message) {

        String receiver = message.getReceiver();
        ServerConnectClientThread getter = serverConnectThreadHashMap.get(receiver);
        getter.sendToTarget(message,MessageType.MESSAGE_SEND_FILE_TO_CLIENT);
    }
}
