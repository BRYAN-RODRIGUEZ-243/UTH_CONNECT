package com.example.doc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class carrera extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adatapateritem;
    AutoCompleteTextView completacion;
    Button button;
    String seleccion;
    String nombre, email, cuenta, password, facultad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrera);
        button = findViewById(R.id.btn_ingresar);

        String[] carrerasUniversitarias = {"Ingeniería en Computacion", "Medicina", "Derecho", "Psicología", "Ingenieria Industrial"};

        Intent intent = getIntent();
        nombre = intent.getStringExtra("NOMBRE");
        email = intent.getStringExtra("NUMERO_CUENTA");
        cuenta = intent.getStringExtra("CORREO");
        password = intent.getStringExtra("pass");

        Toast.makeText(getApplicationContext(), "Seleccionado: " + nombre, Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(carrera.this, Tomar_foto.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("email", email);
                intent.putExtra("cuenta", cuenta);
                intent.putExtra("password", password);
                intent.putExtra("seleccion", seleccion);
                intent.putExtra("facultad", facultad);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, carrerasUniversitarias);

        // AutoCompleteTextView
        autoCompleteTextView = findViewById(R.id.autoComplete);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccion = (String) parent.getItemAtPosition(position);
                // Implementar el if-else para la selección de carreras
                if (seleccion.equals("Ingeniería Informática")) {
                   facultad = "INGENIERIA";
                } else if (seleccion.equals("Medicina")) {
                    facultad = "MEDICINA";
                } else if (seleccion.equals("Derecho")) {
                    facultad = "DERECHO";
                } else if (seleccion.equals("Psicología")) {
                    facultad = "MEDICINA";
                } else if (seleccion.equals("Ingenieria Industrial")) {
                    facultad = "INGENIERIA";
                } else {

                }

                Toast.makeText(getApplicationContext(), "Seleccionado: " + seleccion, Toast.LENGTH_SHORT).show();
            }
        });

    }

}