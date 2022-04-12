package com.project.view;

import com.project.view.LoginFrame;
import com.project.view.UserFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
    private static final long serialVersionUID = 1L;

    public Button(String buttonText, String imageOpen, String imageClose) {

        // 设置按钮背景图像
        ImageIcon iconOpen = new ImageIcon(UserFrame.class.getResource(imageOpen));
        setIcon(iconOpen);

        // 设置鼠标放置在按钮上时的背景图像
        ImageIcon iconClose = new ImageIcon(UserFrame.class.getResource(imageClose));
        setRolloverIcon(iconClose);

        // 设置按钮的大小与图片大小一致
        Dimension dimension = new Dimension(iconOpen.getIconWidth(), iconOpen.getIconHeight());
        this.setSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);


        // 设置文字相对于按钮图像的位置，水平居中，垂直居中
        this.setHorizontalTextPosition(CENTER);
        this.setVerticalTextPosition(CENTER);

        // 不绘制边框
        setBorderPainted(false);

        // 不绘制焦点
        setFocusPainted(false);

        // 不绘制内容区
        setContentAreaFilled(false);

        // 设置焦点控制
        setFocusable(true);

        // 设置按钮边框与边框内容之间的像素数
        setMargin(new Insets(0, 0, 0, 0));

        // 设置文字
        setText(buttonText);

        // 设置文字字体
        Font font = new Font("Arial", Font.BOLD, 15);
        setFont(font);

        // 设置前景色（文字颜色）
        setForeground(Color.white);
    }
}