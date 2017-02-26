package com.detection.util;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHelper {
    public static String SendMail(String mailSubject, String mailContent, String attachmentPath, String mailTo)
            throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.sina.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        final String username = "gzthxf";
        final String password = "gzthxf2017";
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        // -- Create a new message --
        Message msg = new MimeMessage(session);
        
        // -- Set the FROM and TO fields --
        String nick = "";
        try {
            nick = javax.mail.internet.MimeUtility.encodeText("天河区消防安全委员会");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        msg.setFrom(new InternetAddress(nick + " <" + username + "@sina.com>"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo, false));
        msg.setSubject(mailSubject);
        msg.setText(mailContent);
        msg.setSentDate(new Date());
        Transport.send(msg);

        System.out.println("Message sent to " + mailTo);

        return "success.";
    }
}
