package com.project.view;

import com.project.client.service.ManageChatFrame;
import com.project.client.service.UserClientService;
import com.project.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatFrame extends JFrame {

    private JButton button = new JButton("发送");
    private JLabel label = new JLabel("");
    private JTextField textField = new JTextField("", 63);
    private JCheckBox checkBox = new JCheckBox("同意");
    private JTextArea textArea = new JTextArea("", 20, 63);

    private UserClientService clientService;
    private String getter;

    public ChatFrame(String getter, UserClientService clientService) {
        super("与" + getter + "的聊天框");

        this.getter = getter;
        this.clientService = clientService;

        JPanel panel = new JPanel();
        this.setContentPane(panel);

        textArea.setEditable(false);
        textArea.setFont(new Font("标楷体", Font.BOLD, 14));
        textArea.setLineWrap(true); // 自动换行
        textArea.setWrapStyleWord(true); // 断行不断字
        textArea.setText("聊天记录\n");

        panel.add(new JScrollPane(textArea)); // 将JTextArea放入JScrollPane中 (实现滚动)
        panel.add(textField);
        panel.add(button);


        // 注意是给按钮-Listener
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = textField.getText();
                if (str.length() >= 1) {
                    clientService.sendMessageToOne(str, clientService.getUser().getUserName(), getter);

                    textArea.append(getTime() + ":\n");
                    textArea.append("我" + " 说:  " + str + "\n");

                    textField.setText("");
                }
            }
        });

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String str = textField.getText();
                    if (str.length() >= 1) {
                        clientService.sendMessageToOne(str, clientService.getUser().getUserName(), getter);

                        textArea.append(getTime() + ":\n");
                        textArea.append("我" + " 说:  " + str + "\n");

                        textField.setText("");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ManageChatFrame.deleteChatFrame(clientService.getUser().getUserName());
                System.out.println(clientService.getUser().getUserName() + "已退出聊天");
            }
        });

        this.setSize(800, 600);
        this.setVisible(true);

    }


    public void displayMessage(String sender, String content, String time) {
        textArea.append(time + ":\n");
        textArea.append(sender + " 说:  " + content + "\n");
    }

    public static String getTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss").format(new Date());
    }

}
