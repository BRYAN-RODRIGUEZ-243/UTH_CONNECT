package com.example.doc_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListarUsuarios extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private CollectionReference personsCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        personsCollection = db.collection("persons"); // Replace "persons" with your Firestore collection name

        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirestoreRecyclerOptions<Person> options = new FirestoreRecyclerOptions.Builder<Person>()
                .setQuery(personsCollection, Person.class)
                .build();

        adapter = new PersonAdapter(options.getSnapshots());
        recyclerView.setAdapter(adapter);
    }


}
