package com.project.view;

import com.project.client.service.UserClientService;
import com.project.common.Message;

import javax.swing.*;
import java.awt.event.*;

public class LoginAction implements ActionListener, KeyListener {

    // Painting Tool
    private JTextField userName;
    private JTextField password;
    private JFrame loginFrame;

    private static final UserClientService userService = new UserClientService();
    private static UserFrame userFrame = null;

    public static UserClientService getUserService() {
        return userService;
    }

    //创建时，输入界面类中癿输入框
    public LoginAction(JTextField userName, JTextField password, JFrame loginFrame) {
        this.userName = userName;
        this.password = password;
        this.loginFrame = loginFrame;
    }

    public void loginConfirm() {

        String userName = this.userName.getText();
        String pwd = this.password.getText();

        System.out.println("账号输入的是 " + userName);
        System.out.println("密码输入的是 " + pwd);

        if (userService.checkAccount(userName, pwd)) {
            //如果输入正确，弹出新界面
            userFrame = new UserFrame(userService);
            loginFrame.dispose();

        } else {
            //弹出失败界面
            JFrame jf = new JFrame();
            jf.setTitle("登陆失败");
            jf.setSize(300, 100);
            JButton b1 = new JButton("登陆失败，账号和密码不匹配");
            jf.add(b1);
            jf.setLocationRelativeTo(null);
            jf.setVisible(true);
        }
    }

    // 监听方法
    @Override
    public void actionPerformed(ActionEvent e) {
        loginConfirm();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            loginConfirm();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}