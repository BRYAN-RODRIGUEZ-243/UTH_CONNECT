package com.example.doc_app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
public class crear_grupo extends AppCompatActivity {
    private LinearLayout linearLayout;
    private TextView txtNombreGrupo;
    private Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);


        txtNombreGrupo = findViewById(R.id.ArNombres);
        btnCrear = findViewById(R.id.btncrear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear CardView
                CardView cardView = new CardView(crear_grupo.this);
                cardView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                cardView.setCardBackgroundColor(ContextCompat.getColor(crear_grupo.this, android.R.color.white));

                // Crear TextView1
                TextView textView1 = new TextView(crear_grupo.this);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                textView1.setText("Nombre");
                textView1.setId(R.id.nombre); // Ajusta el ID según tus necesidades

                // Agregar TextViews al CardView
                cardView.addView(textView1);

                // Crear nuevo LinearLayout para debajo del botón
                LinearLayout newLinearLayout = new LinearLayout(crear_grupo.this);
                newLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                newLinearLayout.setOrientation(LinearLayout.VERTICAL);

                // Agregar CardView al nuevo LinearLayout
                newLinearLayout.addView(cardView);

                // Agregar nuevo LinearLayout debajo del botón
                linearLayout.addView(newLinearLayout);
            }
        });
    }
}
