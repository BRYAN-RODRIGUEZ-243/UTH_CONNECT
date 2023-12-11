package com.example.doc_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Contenedor extends AppCompatActivity {

    private Button btnMessage;
    String nombre, seleccion;
    TextView nombrec, cuent;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);
        nombrec = findViewById(R.id.textView2);
        cuent = findViewById(R.id.textView3);

        // Obtener datos del intent

            Intent intent = getIntent();
            nombre = intent.getStringExtra("nombre");
            seleccion = intent.getStringExtra("cuenta");
            nombrec.setText(nombre);
            cuent.setText(seleccion);
            nombrec.setText(nombre);
            cuent.setText(seleccion);

        ConstraintLayout messaLayout = findViewById(R.id.messa);
        ConstraintLayout messaLayout2 = findViewById(R.id.messa2);
        ConstraintLayout messaLayout3 = findViewById(R.id.messa3);

         img = findViewById(R.id.image_view21);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abrirGrupo();
            }
        });


        // Configurar click listeners
        messaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirChatActivity();
            }
        });

        messaLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirChatLista();
            }
        });

        messaLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfil();
            }
        });
    }

    private void abrirChatActivity() {
        try {
            Intent intent = new Intent(Contenedor.this, CHAT.class);
            intent.putExtra("nombre", nombre);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Contenedor", "Error al abrir la actividad de chat", e);
            // Puedes manejar el error de otra manera si lo prefieres
        }
    }

    // Método para abrir la actividad de lista de usuarios
    private void abrirChatLista() {
        try {
            Intent intent = new Intent(Contenedor.this, ListarUsuarios.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Contenedor", "Error al abrir la actividad de lista de usuarios", e);
            // Puedes manejar el error de otra manera si lo prefieres
        }
    }

    // Método para abrir la actividad de perfil
    private void abrirPerfil() {
        try {
            Intent intent = new Intent(Contenedor.this, Perfil.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Contenedor", "Error al abrir la actividad de perfil", e);
            // Puedes manejar el error de otra manera si lo prefieres
        }
    }

    //Metodo para abrir lla actividad grupo
    private void abrirGrupo() {
        try {
            Intent intent = new Intent(Contenedor.this, crear_grupo.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Contenedor", "Error al abrir la actividad de perfil", e);
            // Puedes manejar el error de otra manera si lo prefieres
        }
    }

    // Manejo del botón de retroceso
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cerrarAplicacion();
    }

    // Método para cerrar la aplicación
    private void cerrarAplicacion() {
        try {
            finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Contenedor", "Error al cerrar la aplicación", e);
            // Puedes manejar el error de otra manera si lo prefieres
        }
    }
}
