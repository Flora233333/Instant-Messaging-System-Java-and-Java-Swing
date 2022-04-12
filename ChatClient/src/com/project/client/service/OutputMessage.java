package com.project.client.service;

import com.project.common.Message;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutputMessage {

    public static void getOnlineUsersList(Message message) {
        String[] onlineUsers = message.getContent().split(" ");
        System.out.println("=====在线用户=====");
        for (String onlineUser : onlineUsers) {
            System.out.println("用户:" + onlineUser);
        }
        System.out.println("=================");
    }

    public static void getMessage(Message message) {

        String content = message.getContent();
        String sender = message.getSender();
        String receiver = message.getReceiver();
        String Time = message.getSendTime();

        System.out.println(Time + " " + sender + " 对 " + receiver + " 说:\n" + content);
    }

    public static void getMessageAll(Message message) {
        getMessage(message);
    }

    public static void getFile(Message message) throws IOException {
        String sender = message.getSender();
        String receiver = message.getReceiver();
        byte[] data = message.getData();

        System.out.println(sender + "向" + receiver + "发送文件");
        //System.out.println("请输入保存路径");
        // JOptionPane 有点曲线救国的感觉

        String path = "d:\\HappyBirthday.png";

        //String path = JOptionPane.showInputDialog("请输入保存路径");

        //String path = null;
        // 这里主线程的从控制台获取输入的方法会优先执行 无语了
        // 输入完那个才会到这个
        //path = new Scanner(System.in).next();

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();

        System.out.println("已保存至" + path);

    }

}
