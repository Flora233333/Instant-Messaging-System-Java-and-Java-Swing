package com.project.client.service;

import com.project.common.Message;
import com.project.common.MessageType;
import com.project.common.User;
import com.project.view.util.Utility;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @function 提供用户登录和注册等服务类
 */
public class UserClientService {

    private Socket socket;

    private User user = new User();

    public User getUser() {
        return user;
    }

    // 送往服务器验证
    public boolean checkAccount(String username, String password) {
        boolean flag = false;

        user.setUserName(username);
        user.setPassword(password);

        ObjectOutputStream objOutput = null;
        ObjectInputStream objInput = null;

        try { // 1.117.37.18
            socket = new Socket(InetAddress.getByName("172.25.33.105"), 9999);
            objOutput = new ObjectOutputStream(socket.getOutputStream());
            objOutput.writeObject(user); // send out to server
            objOutput.flush();
            //socket.shutdownOutput();

            // read from server
            objInput = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) objInput.readObject();

            if (message.getType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {

                // start client connect server thread
                ClientConnectServerThread clientThread = new ClientConnectServerThread(socket);
                clientThread.start();
                ManageClientConnectServerThread.addToClientThreadHashMap(username, clientThread);

                flag = true;

            } else {
                socket.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }// finally {
        // 只是结束单一任务下关流，没结束所有进程下关流，必报错
//            if (objOutput != null && objInput != null) {
//                try {
//                    objOutput.close();
//                    objInput.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

//        }
        return flag;
    }

    // 向服务器请求在线用户列表
    public void getOnlineUser() {

        Message message = new Message();
        message.setType(MessageType.MESSAGE_GET_ONLINE_ACCOUNT);
        message.setSender(user.getUserName());

        try {
            // 可以试试看直接socket.getOutputStream()行不行
            ObjectOutputStream objOutput =
                    new ObjectOutputStream(ManageClientConnectServerThread
                            .getClientThread(user.getUserName()).getSocket().getOutputStream());
            objOutput.writeObject(message);
            objOutput.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 无异常退出程序
    public void exitService() {
        Message message = new Message();
        message.setType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserName());

        try {
            ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
            objOutput.writeObject(message);
            objOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    // 发送信息
    public void sendMessageToOne(String info, String sender, String getter) {
        Message message = new Message();
        message.setType(MessageType.MESSAGE_SEND_TO_SERVER);
        message.setSender(sender);
        message.setReceiver(getter);
        message.setContent(info);
        message.setSendTime(Message.getTime());

        try {
            ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
            objOutput.writeObject(message);
            objOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToAll(String info, String sender) {
        Message message = new Message();
        message.setType(MessageType.MESSAGE_SEND_ALL_TO_SERVER);
        message.setSender(sender);
        message.setReceiver("所有人");
        message.setContent(info);
        message.setSendTime(Message.getTime());

        try {
            ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
            objOutput.writeObject(message);
            objOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String sender,String getter,String path) {
        Message message = new Message();
        message.setType(MessageType.MESSAGE_SEND_FILE_TO_SERVER);
        message.setSender(sender);
        message.setReceiver(getter);

        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
            byte[] bytes = StreamUtils.streamToByteArray(inputStream);
            message.setData(bytes);

            ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
            objOutput.writeObject(message);
            objOutput.flush();

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
