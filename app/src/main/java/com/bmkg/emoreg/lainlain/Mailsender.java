package com.bmkg.emoreg.lainlain;

import android.os.StrictMode;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Minami on 7/24/2018.
 */

public class Mailsender {


    public Mailsender(final String email, final String password,String subject,String pesan, String tujuan){
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Properties prop=new Properties();
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.socketFactory.port", "465");
                prop.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.port", "465");
                Session session=Session.getDefaultInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email,password);

                    }
                });
                MimeMessage mimes=new MimeMessage(session);
                mimes.addRecipient(Message.RecipientType.TO,new InternetAddress(tujuan));
                mimes.setSubject(subject);
                mimes.setText(pesan);
                Transport.send(mimes);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }

    }

}
