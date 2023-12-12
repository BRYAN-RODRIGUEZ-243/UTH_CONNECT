package com.example.doc_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class crear_grupo extends AppCompatActivity {
    Button crear;
    TextView nombre_grupo;
    ListView listViewGrupos;
    ArrayList<String> listaGrupos;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        // Inicializa los componentes de la interfaz de usuario
        crear = findViewById(R.id.btncrear);
        nombre_grupo = findViewById(R.id.ArNombres);
        listViewGrupos = findViewById(R.id.listViewGrupos);

        // Inicializa la lista de grupos
        listaGrupos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaGrupos);
        listViewGrupos.setAdapter(adapter);

        // Configura el botón para crear un nuevo grupo
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Inicializa Firebase
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // Obtiene una referencia a la base de datos con el nombre "chat"
                DatabaseReference chatReference = database.getReference(nombre_grupo.getText().toString());
                Toast.makeText(getApplicationContext(), "GRUPO CREADO", Toast.LENGTH_SHORT).show();
            }
        });

        // Recupera y muestra la lista de grupos existentes
        mostrarListaDeGrupos();
    }

    private void mostrarListaDeGrupos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference gruposReference = database.getReference("grupos");

        gruposReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaGrupos.clear();

                for (DataSnapshot grupoSnapshot : dataSnapshot.getChildren()) {
                    String nombreGrupo = grupoSnapshot.getKey();
                    listaGrupos.add(nombreGrupo);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error al recuperar la lista de grupos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
