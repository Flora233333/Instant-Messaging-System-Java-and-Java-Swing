package com.project.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

    //用户名
    private JTextField userName;
    //密码
    private JPasswordField pwd;
    //小容器
    private JLabel label1;
    private JLabel label2;
    private JLabel j3;
    private JLabel j4;
    //小按钮
    private JButton button1;
    //复选框
    private JCheckBox c1;
    private JCheckBox c2;

    /**
     * 初始化QQ登录页面
     */
    public LoginFrame() {
        //设置登录窗口标题
        this.setTitle("Test login");
        //去掉窗口的装饰(边框)
        //采用指定的窗口装饰风格
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        //窗体组件初始化
        initComponent();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置布局为绝对定位
        this.setLayout(null);
        this.setBounds(0, 0, 355, 265);
        //设置窗体的图标
        Image img0 = new ImageIcon(LoginFrame.class.getResource("res/logo.jpg")).getImage();
        this.setIconImage(img0);
        //窗体大小不能改变
        this.setResizable(false);
        //居中显示
        this.setLocationRelativeTo(null);
        //窗体显示
        this.setVisible(true);
    }

    /**
     * 窗体组件初始化
     */
    public void initComponent() {
        //创建一个容器,其中的图片大小和setBounds
        Container container = this.getContentPane();
        label1 = new JLabel();

        //设置背景色
        ImageIcon img1 = new ImageIcon(LoginFrame.class.getResource("res/qq.jpg")); // 服了这种路径只能写 / 不能用 \\
        label1.setIcon(img1);
        label1.setBounds(0, 0, 355, 90);

        //qq头像设定
        label2 = new JLabel();
        ImageIcon img2 = new ImageIcon(LoginFrame.class.getResource("res/logo2.png"));
        label2.setIcon(img2);
        label2.setBounds(20, 100, 80, 77);

        //用户名输入框
        userName = new JTextField();
        userName.setBounds(100, 100, 150, 20);
        //注册账号

        j3 = new JLabel("注册账号");
        j3.setBounds(260, 100, 70, 20);

        //密码输入框
        pwd = new JPasswordField();
        pwd.setBounds(100, 130, 150, 20);

        //找回密码
        j4 = new JLabel("找回密码");
        j4.setBounds(260, 130, 70, 20);

        //记住密码
        c1 = new JCheckBox("记住密码");
        c1.setBounds(105, 155, 80, 15);

        //自动登陆
        c2 = new JCheckBox("自动登陆");
        c2.setBounds(185, 155, 80, 15);

        //登陆按钮
        button1 = new JButton("登录");

        //设置字体和颜色和手形指针
        button1.setFont(new Font("宋体", Font.PLAIN, 12));
        button1.setForeground(Color.black);
        button1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button1.setBounds(20, 200, 300, 20);

        //给按钮添加

        //所有组件用容器装载
        this.add(label1);
        this.add(label2);

        this.add(j3);
        this.add(j4);

        this.add(c1);
        this.add(c2);

        this.add(button1);

        //创建监听器对象，幵加给按钮
        LoginAction login_action = new LoginAction(userName, pwd, this);
        button1.addActionListener(login_action);

        container.add(label1);

        userName.addKeyListener(login_action);
        container.add(userName);

        pwd.addKeyListener(login_action);
        container.add(pwd);
    }
}