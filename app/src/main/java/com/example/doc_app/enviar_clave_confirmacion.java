package com.example.doc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

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
    String nombre, seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_clave_confirmacion);

        Intent in = getIntent();
        nombre = in.getStringExtra("nombre");
        seleccion = in.getStringExtra("cuenta");

        // Initialize variables
        correo = "uthconnect987@gmail.com";
        con = "@Connect1234";

        // Create a thread to wait for 1 second and then start a new activity
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);

                // You are starting an activity from a non-UI thread. This could cause issues.
                // It's better to use runOnUiThread or a Handler for UI-related tasks.
                runOnUiThread(() -> startNextActivity());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void startNextActivity() {
        Intent intent = new Intent(enviar_clave_confirmacion.this, confirmar_clave.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("cuenta", seleccion);
        startActivity(intent);
    }

    /*
    Commented-out code:
    - It seems to be related to sending an email, but it's incomplete.
    - You should uncomment and complete this code if you intend to use it.
    - Also, be careful about hardcoding email and password; consider using secure methods for handling credentials.
    */
    /*
    private void sendEmail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", "465");

        try {
            sesion = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo, con);
                }
            });
            if (sesion != null) {
                try {
                    Message message = new MimeMessage(sesion);
                    message.setFrom(new InternetAddress(correo));
                    message.setSubject("Vamos bien");
                    // Set recipients, content, and send the message
                    // You need to complete this part based on your requirements.
                    // message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("recipient@example.com"));
                    // message.setContent("Your email content here", "text/html; charset=utf-8");
                    // Transport.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
