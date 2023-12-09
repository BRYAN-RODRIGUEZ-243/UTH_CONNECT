package com.example.doc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {
Button btn_salir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


    }
    public void eliminar_credenciales() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Eliminar todas las credenciales almacenadas
        editor.remove("user");
        editor.remove("cuenta");
        editor.remove("email");
        editor.remove("password");

        // Realizar el commit y verificar el resultado
        boolean commitSuccess = editor.commit();

        if (commitSuccess) {
            Toast.makeText(this, "Credenciales eliminadas", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error al eliminar las credenciales", Toast.LENGTH_SHORT).show();
        }
    }

}