package com.tongbo.ctbt.pushbranchwarningarea.dic;

public class Constant {
    // 由于测试
    public static final String RABBITMQ_IP = "39.97.185.252";
    public static final String RABBITMQ_USER = "admin";
    public static final String RABBITMQ_PASS = "admin";
    public static final String RABBITMQ_VIRTUALHOST = "my_vhost";
    public static final String EXCHANGE_NAME_DIRECT = "direct_logs";
    public static final String EXCHANGE_NAME_FANOUT = "fanout_logs";

    // 用于舟山智慧海渔综合管理服务平台
    public static final String RABBITMQ_IP_BEIDOU = "192.168.1.22";
    public static final int RABBITMQ_PORT_BEIDOU = 5672;
    public static final String RABBITMQ_USER_BEIDOU = "admin";
    public static final String RABBITMQ_PASS_BEIDOU = "Ctbt&Anke,,310144520";
    public static final String RABBITMQ_VIRTUALHOST_BEIDOU = "admin_vhost";
    public static final String EXCHANGE_NAME_BEIDOU = "fanout.ctbt.info";
    public static final String QUEUE_NAME_BEIDOU = "ctbtinfo3_wsf";
    public static final String ROUTING_BEIDOU = "ctbt";

    // 舟山109推送程序生产的报位消息，
    public static final String RABBITMQ_IP_BEIDOU_PUSH = "192.168.1.15";
    public static final int RABBITMQ_PORT_BEIDOU_PUSH = 5672;
    public static final String RABBITMQ_USER_BEIDOU_PUSH = "tongbo";
    public static final String RABBITMQ_PASS_BEIDOU_PUSH = "anke";
    public static final String RABBITMQ_VIRTUALHOST_BEIDOU_PUSH = "push109";
    public static final String EXCHANGE_NAME_BEIDOU_PUSH = "beidou-fanout";
    public static final String QUEUE_NAME_BEIDOU_PUSH = "beidou-areaWarning";
    public static final String ROUTING_BEIDOU_PUSH = "ctbt";



}
