package com.tongbo.ctbt.pushbranchwarningarea.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * // 发送邮件
 */
public class EmailUtil {

    public static void sendEmail(String to, String title, String text) {

        // 发件人电子邮箱
        String from = "tongbo_code@163.com";

        // 指定发送邮件的主机为 localhost
        String host = "smtp.163.com";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = null;
        try {

            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            // 获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tongbo_code@163.com", "HSDWJZKVZBSIVSAP"); // 发件人邮件用户名、授权码
                }
            });

            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject(title);

            // 设置消息体
            message.setText(text);

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
		EmailUtil.sendEmail("chuanqijob@163.com", "test", "巴拉巴拉");
    }
}
