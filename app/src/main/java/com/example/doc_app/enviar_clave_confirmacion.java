package com.example.doc_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.Button;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class enviar_clave_confirmacion extends AppCompatActivity {

    String correo;
    String con;
    Button enviar;
    Session sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_clave_confirmacion);

        // Initialize variables
        correo = "uthconnect987@gmail.com";
        con = "@Connect1234";

        // Create a thread to wait for 1 second and then start a new activity
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    startNextActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void startNextActivity() {
        Intent intent = new Intent(enviar_clave_confirmacion.this, confirmar_clave.class);
        startActivity(intent);
    }
/*
    public void enviar() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", "465");

        try {
            sesion = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo,con) ;
                }
            });
            if(sesion != null){
                Message message = new MimeMessage(sesion);
                message.setTarget();
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Vamos bien");
                message.setRecipies(Message.Recipient)


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
