package com.project.server.sevice;

import com.project.common.Message;
import com.project.common.MessageType;
import com.project.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @function 服务器主体代码
 * 监听端口 Server
 * 等待连接 Server
 * 保持通信 ServerConnectClientThread
 */

public class Server {

    private ServerSocket server;

    private static ConcurrentHashMap<String, User> textUser = new ConcurrentHashMap<>();

    static {
        textUser.put("pxc", new User("pxc", "123"));
        textUser.put("hxk", new User("hxk", "234"));
        textUser.put("dzl", new User("dzl", "345"));
        textUser.put("ktp", new User("ktp", "456"));
    }

    public Server() {
        System.out.println("服务器已启动，监听9999端口");

        try { // 建议端口写在配置文件中
            server = new ServerSocket(9999);

            while (true) {

                Socket socket = server.accept();

                ObjectInputStream objInput = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());

                User user = (User) objInput.readObject();

                System.out.println(user.getUserName() + " " + user.getPassword() + "请求登录");
                // 建议以后升级用DataBase
                Message message = new Message();
                // 验证登录
                if (checkUser(user.getUserName(), user.getPassword())) {
                    message.setType(MessageType.MESSAGE_LOGIN_SUCCEED);

                    objOutput.writeObject(message);
                    objOutput.flush();
                    //socket.shutdownOutput();

                    ServerConnectClientThread connectThread = new ServerConnectClientThread(socket, user.getUserName());
                    connectThread.start();
                    System.out.println("用户ID=" + user.getUserName() + "  密码=" + user.getPassword() + " 登录成功");

                    ManageConnectThread.addToServerThreadHashMap(user.getUserName(), connectThread);

                } else {
                    System.out.println("用户ID=" + user.getUserName() + "  密码=" + user.getPassword() + " 登录失败");
                    message.setType(MessageType.MESSAGE_LOGIN_FAILED);

                    objOutput.writeObject(message);
                    objOutput.flush();

                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("all")
    public boolean checkUser(String userName, String pwd) {
        User user = textUser.get(userName);

        if (user == null)
            return false;
        if (!user.getPassword().equals(pwd))
            return false;
        if (ManageConnectThread.getClientThread(userName) != null)
            return false; // 防止同一用户多次登录

        return true;
    }
}
