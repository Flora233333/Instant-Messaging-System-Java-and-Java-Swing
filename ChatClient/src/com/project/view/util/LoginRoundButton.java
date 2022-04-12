package com.project.view.util;

import javax.swing.*;
import java.awt.*;

public class LoginRoundButton extends JButton {
    Color bgcolor = UColor.ButtonDefaultColor;
    Boolean Cover = false;

    public LoginRoundButton(String ins) {
        super(ins);
    }

    public void BgHigh() {
        bgcolor = UColor.ButtonCoverColor;
        this.repaint();
    }

    public void BgLow() {
        bgcolor = UColor.ButtonDefaultColor;
        this.repaint();
    }

    protected void paintBorder(Graphics g) {
        int h = getHeight();// 从JComponent类获取高宽
        int w = getWidth();
        Graphics2D g2d = (Graphics2D) g.create();
        Shape shape = g2d.getClip();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(shape);
        g2d.setStroke(new BasicStroke(7f));
        g2d.setColor(UColor.OperatorBackgroundColor);
        g2d.drawRoundRect(0, 0, w - 1, h - 1, 15, 15);
        g2d.dispose();
        this.setBackground(bgcolor);
        super.paintBorder(g2d);
    }
}