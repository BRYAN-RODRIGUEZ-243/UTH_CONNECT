package com.example.doc_app;

import static java.lang.System.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;

public class acceso extends AppCompatActivity {
    Button btnRegistrarse, btnIniciar;
    FirebaseFirestore mfirestore;
    EditText edtCorreo, edtPassword,edtCuenta;

    String nombre ;
    String account ;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);
        mfirestore = FirebaseFirestore.getInstance();

        edtCuenta = findViewById(R.id.edtCorreo);
        edtPassword = findViewById(R.id.edtPassword);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        cargarPreferencias();

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarPreferencias();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(acceso.this, registrarse.class);
                startActivity(intent);

            }
        });
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        // Verificar si el archivo "credenciales" existe
        if (preferences.contains("cuenta") && preferences.contains("password")) {
            // El archivo "credenciales" existe, puedes recuperar los valores
             nombre = preferences.getString("user", "");
             account = preferences.getString("cuenta", "");
             pass = preferences.getString("password", "");
            edtPassword.setText(pass);
            edtCuenta.setText(account);
            Toast.makeText(this, "Espera...", Toast.LENGTH_LONG).show();

           // confirmar_usuario_firebase();
        } else {
            Toast.makeText(this, "Datos no guardados", Toast.LENGTH_SHORT).show();
        }

    }

    public void confirmar_usuario_firebase() {
        // Validación de Entrada
        String cuentaIngresado = edtCuenta.getText().toString().trim();
        String pass_usuario = edtPassword.getText().toString().trim();

        if (cuentaIngresado.isEmpty() || pass_usuario.isEmpty()) {
            Toast.makeText(this, "Ingrese correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        mfirestore.collection("usuarios")
                .whereEqualTo("N.Cuenta", edtCuenta.getText().toString().trim())
                .whereEqualTo("password", edtPassword.getText().toString().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                // Usuario no existe, ejecutar código de registro
                                Intent intent = new Intent(acceso.this, Contenedor.class);

                                intent.putExtra("nombre", nombre);
                                intent.putExtra("cuenta", account);
                                startActivity(intent);
                            } else {
                                Toast.makeText(acceso.this, "Datos no guardados", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        // Cierra la aplicación cuando se presiona el botón de retroceso
        super.onBackPressed();
        finishAffinity();
    }

}

