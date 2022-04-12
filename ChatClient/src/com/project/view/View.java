package com.project.view;


import com.project.client.service.UserClientService;
import com.project.view.util.Utility;


public class View {

    private boolean loop = true;
    private int key;
    private UserClientService userService = new UserClientService();

    public static void main(String[] args) {
        new View().menu();
    }

    private void menu() {

        //一级菜单

        while (loop) {

            String info = null;
            String getter = null;

            System.out.println("登陆界面");
            System.out.println("\t\t1.登录系统");
            System.out.println("\t\t9.退出系统");
            key = Utility.readInt();

            switch (key) {
                case 1:
                    System.out.println("账号");
                    String userName = Utility.readString(10);
                    System.out.println("密码");
                    String password = Utility.readString(10);

                    //**服务器远端验证**//

                    if (userService.checkAccount(userName, password)) {
                        System.out.println("登陆成功");

                        //二级菜单
                        System.out.println("用户菜单");
                        System.out.println("1.用户列表");
                        System.out.println("2.群发消息");
                        System.out.println("3.私聊消息");
                        System.out.println("4.发送文件");
                        System.out.println("9.退出系统");

                        while (loop) {

                            key = Utility.readInt();

                            switch (key) {
                                case 1:
                                    userService.getOnlineUser();
                                    break;
                                case 2:
                                    System.out.println("请输入聊天内容");
                                    info = Utility.readString(12);
                                    userService.sendMessageToAll(info, userName);
                                    break;
                                case 3:
                                    System.out.println("请输入私聊用户");
                                    getter = Utility.readString(12);
                                    System.out.println("请输入聊天内容");
                                    info = Utility.readString(12);
                                    userService.sendMessageToOne(info, userName, getter);
                                    break;
                                case 4:
                                    System.out.println("请输入接收文件用户");
                                    getter = Utility.readString(12);
                                    System.out.println("请输入文件地址");
                                    String path = Utility.readString(100);
                                    userService.sendFile(userName, getter, path);
                                    break;
                                case 9:
                                    System.out.println("退出系统");
                                    userService.exitService();
                                    break;
                            }
                        }

                    } else {
                        System.out.println("账号或密码错误或同一用户只能登录一次");
                    }

                    break;
                case 9:
                    System.out.println("退出系统");
                    userService.exitService();
                    break;
                default:
                    System.out.println("错误");
                    break;
            }
        }
    }
}
