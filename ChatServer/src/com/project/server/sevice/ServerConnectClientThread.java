package com.project.server.sevice;

import com.project.common.Message;
import com.project.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 多线程保持通信
 */

public class ServerConnectClientThread extends Thread {

    private Socket socket;
    private String userName; // 读取标记用户ID
    private boolean loop = true;

    public ServerConnectClientThread(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
    }

    @Override
    public void run() {
        System.out.println(userName + "连接服务器成功");

        while (loop) {
            try {
                ObjectInputStream objInput = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objInput.readObject();

                switch (message.getType()) {

                    case MessageType.MESSAGE_GET_ONLINE_ACCOUNT:
                        System.out.println(message.getSender() + "请求在线用户列表");
                        sendOnlineUsers(message.getSender());
                        break;

                    case MessageType.MESSAGE_CLIENT_EXIT:
                        System.out.println(message.getSender() + "退出登录");
                        ManageConnectThread.exitUser(message.getSender());
                        break;

                    case MessageType.MESSAGE_SEND_TO_SERVER:
                        System.out.println(message.getSendTime() + " " + message.getSender() + " 对 " + message.getReceiver() + " 发送信息");
                        ManageConnectThread.transferMessage(message);
                        break;

                    case MessageType.MESSAGE_SEND_ALL_TO_SERVER:
                        System.out.println(message.getSendTime() + " " + message.getSender() + " 对 " + message.getReceiver() + " 发送信息");
                        ManageConnectThread.transferMessageToAll(message);
                        break;

                    case MessageType.MESSAGE_SEND_FILE_TO_SERVER:
                        System.out.println(message.getSender() + "向" + message.getReceiver() + "发送文件");
                        ManageConnectThread.transferFile(message);
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

                try { // 处理下客户端意外退出的异常
                    socket.close();
                    ManageConnectThread.removeClientThread(userName);
                    exitThread();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public void sendOnlineUsers(String getter) throws IOException {
        Message message = new Message();
        String onlineUsers = ManageConnectThread.getOnlineUsers();
        ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());

        message.setType(MessageType.MESSAGE_RETURN_ONLINE_ACCOUNT);
        message.setContent(onlineUsers);
        message.setReceiver(getter);

        objOutput.writeObject(message);
        objOutput.flush();

        //** objOutput.close(); ** 千万别关流，关流客户端必报错
        // 你这里关流客户端异常为: null , 这里异常为 SocketException
        //推测为关闭IO流，会自动关闭 Socket ?
    }

    public void sendToTarget(Message message, String messageType) {
        message.setType(messageType);

        try {
            ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
            objOutput.writeObject(message);
            objOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void exitThread() {
        loop = false;
    }
}
