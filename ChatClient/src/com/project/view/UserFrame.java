package com.project.view;

import com.project.client.service.ManageChatFrame;
import com.project.client.service.UserClientService;
import com.project.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class UserFrame extends JFrame {

    private JPanel panel = new Panel();

    private Button button;
    private JLabel label;
    private JLabel ImageLabel;
    private JLabel nameLabel;

    private UserClientService clientService;
    private static Message message;

    private int mouseAtX;
    private int mouseAtY;
    private boolean flag = false;
    private HashMap<String, JLabel> labelHashMap = new HashMap<>();

//    public static void main(String[] args) {
//        new UserFrame();
//    }

    public UserFrame(UserClientService clientService) {

        this.clientService = clientService;

        this.setLocationRelativeTo(null); // 去除JFrame标题
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.setContentPane(panel);
        this.setLayout(null); // 设置绝对位置，必须要setBounds才会显示
        this.setLocationRelativeTo(null); // 窗口居中

        panel.setBounds(0, 0, 400, 800);
        panel.setLayout(null);

        initComponent();

        this.setSize(400, 800);
        this.setVisible(true);

//        重绘设置
//        Thread thread = new Thread(this);
//        thread.setDaemon(true);
//        thread.start();
    }

    private class Panel extends JPanel {

        public Panel() {
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);


            g.setColor(new Color(193, 255, 193));
            g.fillRect(0, 0, 400, 150);

            g.setColor(new Color(253, 245, 230));
            g.fillRect(0, 150, 400, 550);

            g.setColor(new Color(193, 255, 193));
            g.fillRect(0, 700, 400, 100);

            if (flag) {
                getOnlineUsersList(message);
                flag = false;
            }

        }

    }

    public void initComponent() {
        Icon icon = new ImageIcon(UserFrame.class.getResource("res/用户列表.png"));
        ImageLabel = new JLabel(icon, JLabel.CENTER);
        ImageLabel.setBounds(40, 190, icon.getIconWidth(), icon.getIconHeight());

        label = new JLabel("在线用户列表", JLabel.LEFT);
        Font font = new Font("微软雅黑", Font.ITALIC + Font.BOLD, 18);
        label.setFont(font);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        label.setBounds(100, 200, metrics.stringWidth(label.getText()), metrics.getHeight());
        // metrics.stringWidth(label.getText()), metrics.getHeight() 得到字符串宽和高的方法

        nameLabel = new JLabel(clientService.getUser().getUserName(), JLabel.CENTER);
        Font nameFont = new Font("楷体", Font.ITALIC + Font.BOLD, 35);
        nameLabel.setFont(nameFont);
        FontMetrics nameMetrics = nameLabel.getFontMetrics(nameLabel.getFont());
        nameLabel.setBounds(100 - nameMetrics.getHeight() / 2, 50, nameMetrics.stringWidth(label.getText()), nameMetrics.getHeight());
        // (100 - nameMetrics.getHeight() / 2) 居中显示算法，至于为什么是100，而不是200，我也不知道(调出来就是这样最符合)

        button = new Button("", "res/关闭 (2).png", "res/关闭 (4).png");
        button.setBounds(330, 10, 48, 48);

        this.add(button);
        this.add(label);
        this.add(ImageLabel);
        this.add(nameLabel);

        this.setMovingMouseAction();
        this.setLabelAction(label);
        this.setCloseButtonAction(button);

    }

    public void getOnlineUsersList(Message message) {
        String[] onlineUsers = null;

        if (message != null)
            onlineUsers = message.getContent().split(" ");

        if (onlineUsers != null && onlineUsers.length > 0) {
            for (int i = 0; i < onlineUsers.length; i++) {

                JLabel userLabel = new JLabel(onlineUsers[i], JLabel.LEFT);
                Font font = new Font("微软雅黑", Font.ITALIC + Font.BOLD, 16);
                userLabel.setFont(font);
                FontMetrics userMetrics = userLabel.getFontMetrics(userLabel.getFont());
                userLabel.setBounds(110, 250 + 50 * i, userMetrics.stringWidth(userLabel.getText()), userMetrics.getHeight());
                setUserLabelAction(userLabel);
                labelHashMap.put(onlineUsers[i], userLabel);
                panel.add(userLabel);
            }
        }
        System.out.println(clientService.getUser().getUserName() + "完成获取在线用户列表");
    }

    public static void setMessage(Message message) {
        UserFrame.message = message;
    }

    // 实现鼠标拖动窗口
    public void setMovingMouseAction() {
        this.setUndecorated(true);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseAtX = e.getPoint().x;
                mouseAtY = e.getPoint().y;
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation((e.getXOnScreen() - mouseAtX), (e.getYOnScreen() - mouseAtY));
            }
        });
    }

    // 按钮点击
    public void setCloseButtonAction(Button button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientService.exitService();
            }
        });
    }

    // 文字标签事件处理
    public void setLabelAction(JLabel label) {

        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientService.getOnlineUser();

                try { // 纯纯因为服务器发过来有网络延迟所有sleep
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.out.println("getMessage " + message.getContent());
                flag = true;
                panel.repaint();

                // remove 是因为反正不 repaint 字就不会消失
                // 同时全部 remove 相当于刷新一次 panel,下次直接加，不会出现同一控件多次add，或不同控件重叠
                for (Map.Entry<String, JLabel> entry : labelHashMap.entrySet()) {
                    panel.remove(entry.getValue());
                }
                labelHashMap.clear();

                //System.out.println(panel.countComponents());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override // 鼠标移入
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.BLUE);
            }

            @Override // 鼠标移出
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLACK);
            }
        });

    }

    public void setUserLabelAction(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChatFrame chatFrame = new ChatFrame(label.getText(), clientService);
                ManageChatFrame.saveInChatFrame(clientService.getUser().getUserName(), chatFrame);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLACK);
            }
        });
    }
}
