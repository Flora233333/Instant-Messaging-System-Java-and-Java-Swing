package com.project.view.util;

import javax.swing.*;
import java.awt.*;

public class LoginRoundTextBox extends JTextField {
    Color bordercolor = UColor.InputDefaultBorderColor;
    boolean Cover = false;

    public void BorderHigh() {
        bordercolor = UColor.InputCoverBorderColor;
        Cover = true;
        this.repaint();
    }

    public void BorderLow() {
        bordercolor = UColor.InputDefaultBorderColor;
        Cover = false;
        this.repaint();
    }

    protected void paintBorder(Graphics g) {
        int h = getHeight();// 从JComponent类获取高宽
        int w = getWidth();
        Graphics2D g2d = (Graphics2D) g.create();
        Shape shape = g2d.getClip();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(shape);
        g2d.setColor(bordercolor);
        if (Cover) {
            g2d.setStroke(new BasicStroke(1.8f));
        } else {
            g2d.setStroke(new BasicStroke(1.0f));
        }
        g2d.drawRoundRect(0, 0, w - 1, h - 1, 5, 5);
        g2d.dispose();
        super.paintBorder(g2d);
    }
}