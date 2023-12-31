package com.example.doc_app;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListarUsuarios extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private CollectionReference personsCollection;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        personsCollection = db.collection("usuario");

        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrar resultados cuando se cambia el texto de búsqueda
                if (!TextUtils.isEmpty(newText)) {
                    searchFirestore(newText);
                } else {
                    // Si el texto de búsqueda está vacío, muestra todos los usuarios
                    setupRecyclerView("");
                }
                return true;
            }
        });

        setupRecyclerView("");
    }

    private void searchFirestore(String query) {
        // Filtra los resultados de Firestore según la consulta
        Query searchQuery = personsCollection.whereEqualTo("nombre", query); // Reemplaza "nombre" con el campo que deseas buscar
        FirestoreRecyclerOptions<Person> options = new FirestoreRecyclerOptions.Builder<Person>()
                .setQuery(searchQuery, Person.class)
                .build();

        adapter.updateOptions(options);
    }

    private void setupRecyclerView(String query) {
        // Configura el RecyclerView con los datos de Firestore
        Query baseQuery = personsCollection.orderBy("nombre"); // Reemplaza "nombre" con el campo por el cual deseas ordenar
        if (!TextUtils.isEmpty(query)) {
            baseQuery = personsCollection.whereEqualTo("nombre", query);
        }

        FirestoreRecyclerOptions<Person> options = new FirestoreRecyclerOptions.Builder<Person>()
                .setQuery(baseQuery, Person.class)
                .build();

        adapter = new PersonAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
