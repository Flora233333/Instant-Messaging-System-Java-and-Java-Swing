package com.project.client.service;

import java.util.HashMap;

/**
 * 管理用户线程类
 */

public class ManageClientConnectServerThread {

    private static HashMap<String, ClientConnectServerThread> clientThreadHashMap = new HashMap<>();

    public static void addToClientThreadHashMap(String userID, ClientConnectServerThread clientThread) {
        clientThreadHashMap.put(userID, clientThread);
    }

    public static ClientConnectServerThread getClientThread(String userName) {
        return clientThreadHashMap.get(userName);
    }
}
