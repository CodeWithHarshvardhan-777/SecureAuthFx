package org.hauthfx.secureauthfx;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTP {
    protected static int otp;
    public static boolean SendOtp(String sender_Email)
    {
        boolean send = false;
        final String my_Email = "h87539994@gmail.com";
        final String app_Password = "jzdf tvfc icul czep ";

        //class for otp
        GenrateOtp genrateOtp = new GenrateOtp();
        otp = genrateOtp.otpGenrater();

        String subject = "Hi This is Me Harshvardhan.";
        String body = "<html>" +
                "<head>" +
                "<body style = 'font-family: arial;'>" +
                "<center><h1 style = 'color: green; font-weight: bold;'>Thanks For Your Support.</h1></center><hr>" +
                "<br>" +
                "<center><h2 style = 'color: black; background-color: green; border-radius: 5px; padding: 5px;'>Your OPT is : " + otp + "</center></body>" +
                "</html>";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(my_Email,app_Password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(my_Email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sender_Email));
            message.setSubject(subject);
            message.setContent(body,"text/html");

            Transport.send(message);
            System.out.println("Send Successfully....");
            return send = true;
        } catch (Exception e) {
           return send = false;
        }
    }
    public static void main(String[] args) {
        SendOTP.SendOtp("pandeharshavardhan@gmail.com");
    }
}
