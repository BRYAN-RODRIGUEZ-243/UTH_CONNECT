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
    String seleccion = "";
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

      //  Toast.makeText(getApplicationContext(), "Seleccionado: " + nombre+email, Toast.LENGTH_SHORT).show();

        button.setOnClickListener(v -> {
            if(seleccion.equals("")){
                Toast.makeText(getApplicationContext(), "Debes seleccionar tu carrera", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent1 = new Intent(carrera.this, Tomar_foto.class);
                intent1.putExtra("nombre", nombre);
                intent1.putExtra("email", email);
                intent1.putExtra("cuenta", cuenta);
                intent1.putExtra("password", password);
                intent1.putExtra("seleccion", seleccion);
                intent1.putExtra("facultad", facultad);
                startActivity(intent1);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, carrerasUniversitarias);
        autoCompleteTextView = findViewById(R.id.autoComplete);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            seleccion = (String) parent.getItemAtPosition(position);
            // Implementar el if-else para la selección de carreras
            if (seleccion.equals("Ingeniería en Computacion")) {
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

        });
    }
}