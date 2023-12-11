package com.example.doc_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class registrarse extends AppCompatActivity {
    Button button;
    EditText editTextNombre, editTextNumeroCuenta, editTextCorreo, editTextpass;
    String nombre, numeroCuenta, correo, password;

    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        editTextNombre = findViewById(R.id.edt_name);
        editTextNumeroCuenta = findViewById(R.id.edt_ncuenta);
        editTextCorreo = findViewById(R.id.edt_email);
        editTextpass = findViewById(R.id.edt_pass);
        btnRegistrar = findViewById(R.id.id_persistent);
        button = findViewById(R.id.btn_login);

        CheckBox checkBox = findViewById(R.id.id_persistent);

        // Agrega un listener para el evento de clic en el CheckBox
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nombre = editTextNombre.getText().toString();
                numeroCuenta = editTextNumeroCuenta.getText().toString();
                correo = editTextCorreo.getText().toString();
                password = editTextpass.getText().toString();
                if (nombre.isEmpty() || numeroCuenta.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Datos no guardados \n Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();

                } else {
                    guardar_datos_persistencia();
                }
            }
        });


        button.setOnClickListener(v -> {
            nombre = editTextNombre.getText().toString();
            numeroCuenta = editTextNumeroCuenta.getText().toString();
            correo = editTextCorreo.getText().toString();
            password = editTextpass.getText().toString();
            if (nombre.isEmpty() || numeroCuenta.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();
            } else {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {

        Intent intent = new Intent(registrarse.this, carrera.class);
        // Puedes pasar los datos como extras en el Intent
        intent.putExtra("NOMBRE", nombre);
        intent.putExtra("NUMERO_CUENTA", numeroCuenta);
        intent.putExtra("CORREO", correo);
        intent.putExtra("pass", password);
        startActivity(intent);
     //   Toast.makeText(this, "Selecciona tu carrera"+nombre+numeroCuenta+correo+password, Toast.LENGTH_SHORT).show();
    }

    public void guardar_datos_persistencia() {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String usuario = editTextNombre.getText().toString().trim();
        String Cuenta = editTextNumeroCuenta.getText().toString().trim();
        String mail = editTextCorreo.getText().toString().trim();
        String Password = editTextpass.getText().toString().trim();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", usuario);
        editor.putString("cuenta", Cuenta);
        editor.putString("email", mail);
        editor.putString("password", Password);

// Realizar el commit y verificar el resultado
        boolean commitSuccess = editor.commit();

        if (commitSuccess) {
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();

        } else {
            // Hubo un problema al guardar los datos
            Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
        }

    }
}
