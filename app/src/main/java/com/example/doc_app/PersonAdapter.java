package com.example.doc_app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PersonAdapter extends FirestoreRecyclerAdapter<Person, PersonAdapter.PersonViewHolder> {

    public PersonAdapter(@NonNull FirestoreRecyclerOptions<Person> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PersonViewHolder holder, int position, @NonNull Person model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);
        return new PersonViewHolder(view);
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreTextView;
        private TextView facultadTextView;
        private TextView carreraTextView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.firstname);
            facultadTextView = itemView.findViewById(R.id.lastname);
            carreraTextView = itemView.findViewById(R.id.age);
        }

        public void bind(Person person) {
            // Bind data to TextViews
            nombreTextView.setText(person.getNombre());
            facultadTextView.setText(person.getFacultad());
            carreraTextView.setText(person.getCarrera());
        }
    }
}
