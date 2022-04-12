package com.project.common;

public interface MessageType {

    // 不同常量的值，表示不同的消息类型
    String MESSAGE_LOGIN_SUCCEED = "1";
    String MESSAGE_LOGIN_FAILED = "2";

    String MESSAGE_COMMON_MES = "3";

    String MESSAGE_GET_ONLINE_ACCOUNT = "4";
    String MESSAGE_RETURN_ONLINE_ACCOUNT = "5";

    String MESSAGE_CLIENT_EXIT = "6";

    String MESSAGE_SEND_TO_SERVER = "7";
    String MESSAGE_SEND_TO_CLIENT = "8";
    String MESSAGE_SEND_ALL_TO_SERVER = "9";
    String MESSAGE_SEND_ALL_TO_CLIENT = "10";
    String MESSAGE_SEND_FILE_TO_SERVER = "11";
    String MESSAGE_SEND_FILE_TO_CLIENT = "12";
}