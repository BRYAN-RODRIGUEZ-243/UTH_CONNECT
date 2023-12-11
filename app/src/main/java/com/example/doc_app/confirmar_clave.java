package com.example.doc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class confirmar_clave extends AppCompatActivity {
    Button  btnIniciar;
    String nombre,seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_clave);

        btnIniciar = findViewById(R.id.sendCodeButton);

        btnIniciar.setOnClickListener(view -> {
            Intent intent = getIntent();
            nombre = intent.getStringExtra("nombre");
            seleccion = intent.getStringExtra("email");
            Intent intent1 = new Intent(confirmar_clave.this, Contenedor.class);
            intent1.putExtra("nombre", nombre);
            intent1.putExtra("cuenta", seleccion);
            Toast.makeText(this, "Selecciona tu carrera"+nombre+seleccion, Toast.LENGTH_SHORT).show();

            startActivity(intent1);
        });
    }

}