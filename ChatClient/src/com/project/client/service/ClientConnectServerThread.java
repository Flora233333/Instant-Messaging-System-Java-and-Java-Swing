package com.project.client.service;

import com.project.common.Message;
import com.project.common.MessageType;
import com.project.view.ChatFrame;
import com.project.view.UserFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 用户多线程通信类
 */

public class ClientConnectServerThread extends Thread {

    private Socket socket;
    private boolean loop = true;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 循环读取服务器传回的信息
        while (loop) {
            try {
                ObjectInputStream objInput = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objInput.readObject();

                switch (message.getType()) {

                    case MessageType.MESSAGE_RETURN_ONLINE_ACCOUNT:
                        OutputMessage.getOnlineUsersList(message);
                        UserFrame.setMessage(message);
                        break;

                    case MessageType.MESSAGE_CLIENT_EXIT:
                        loop = false;
                        break;

                    case MessageType.MESSAGE_SEND_TO_CLIENT:
                        OutputMessage.getMessage(message);
                        ManageChatFrame.distributeMessage(message);
                        break;

                    case MessageType.MESSAGE_SEND_ALL_TO_CLIENT:
                        OutputMessage.getMessageAll(message);
                        break;

                    case MessageType.MESSAGE_SEND_FILE_TO_CLIENT:
                        OutputMessage.getFile(message);
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
