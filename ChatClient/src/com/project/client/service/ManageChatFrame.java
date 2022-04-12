package com.project.client.service;

import com.project.common.Message;
import com.project.view.ChatFrame;

import java.util.HashMap;

public class ManageChatFrame {

    private static HashMap<String, ChatFrame> chatFrameHashMap = new HashMap<>();

    public static void saveInChatFrame(String userName, ChatFrame chat) {
        chatFrameHashMap.put(userName, chat);
    }

    public static ChatFrame getChatFrame(String userName) {
        return chatFrameHashMap.get(userName);
    }

    public static void deleteChatFrame(String userName) {
        chatFrameHashMap.remove(userName);
    }

    public static void distributeMessage(Message message) {
        String content = message.getContent();
        String sendTime = message.getSendTime();
        String sender = message.getSender();
        String receiver = message.getReceiver();

        ChatFrame chat = getChatFrame(receiver);

        chat.displayMessage(sender, content, sendTime);
    }
}// 好像有bug，因为到这个类，必然getter是对的，重新判断一下好像没必要