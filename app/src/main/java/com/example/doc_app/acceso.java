package com.example.doc_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class acceso extends AppCompatActivity {
    Button btnRegistrarse, btnIniciar;
    FirebaseFirestore mfirestore;
    EditText edtPassword, edtCuenta;

    String nombre;
    String account;
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

        btnIniciar.setOnClickListener(view -> {
            confirmar_usuario_firebase();
        });

        btnRegistrarse.setOnClickListener(view -> {

            Intent intent = new Intent(acceso.this, registrarse.class);
            startActivity(intent);

        });
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);


        // El archivo "credenciales" existe, puedes recuperar los valores
        nombre = preferences.getString("user", "");
        account = preferences.getString("cuenta", "");
        pass = preferences.getString("password", "");
        edtPassword.setText(pass);
        edtCuenta.setText(account);
        Toast.makeText(this, "Espera...", Toast.LENGTH_LONG).show();

    }

    public void confirmar_usuario_firebase() {
        // Validación de Entrada
        String cuentaIngresado = edtCuenta.getText().toString().trim();
        String pass_usuario = edtPassword.getText().toString().trim();

        if (cuentaIngresado.isEmpty() || pass_usuario.isEmpty()) {
            Toast.makeText(this, "Ingrese correo y contraseña", Toast.LENGTH_SHORT).show();

        } else {
            mfirestore.collection("usuario")
                    .whereEqualTo("N.Cuenta", cuentaIngresado)
                    .whereEqualTo("password", pass_usuario)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().isEmpty()) {
                                    Intent intent = new Intent(acceso.this, Contenedor.class);
                                    intent.putExtra("nombre", nombre);
                                    intent.putExtra("cuenta", account);
                                    startActivity(intent);
                                } else {
                                  
                                    Toast.makeText(acceso.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(acceso.this, "Datos no guardados", Toast.LENGTH_SHORT).show();
                            }
                             /*else{
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }*/
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        // Cierra la aplicación cuando se presiona el botón de retroceso
        super.onBackPressed();
        finishAffinity();
    }

}

