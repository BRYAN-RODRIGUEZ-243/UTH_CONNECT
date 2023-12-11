package com.example.doc_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tomar_foto extends AppCompatActivity {
    String currentPhotoPath;
    ImageView Img;

    Uri selectedImage;
    StorageReference mstorage;
    FirebaseFirestore mfirestore;

    FrameLayout frameLayoutDerecho;
    TextView name, mail, account, pass, select;
    String nombre;
    String email;
    String cuenta;
    String password;
    String seleccion;
    String facultad;
    Button button;
    static final int Peticion_ElegirGaleria = 103;

    static final int Peticion_AccesoCamara = 101;
    static final int Peticion_TomarFoto = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_foto);
        mfirestore = FirebaseFirestore.getInstance();
        mstorage = FirebaseStorage.getInstance().getReference();
        frameLayoutDerecho = findViewById(R.id.frameLayout);
        button = findViewById(R.id.btn_login);

        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        email = intent.getStringExtra("email");
        cuenta = intent.getStringExtra("cuenta");
        password = intent.getStringExtra("password");
        seleccion = intent.getStringExtra("seleccion");
        facultad = intent.getStringExtra("facultad");


        name = findViewById(R.id.tvNombre);
        mail = findViewById(R.id.mail);
        account = findViewById(R.id.tvCuenta);
        pass = findViewById(R.id.tvfacultad);
        select = findViewById(R.id.enviado);

        name.setText("NOMBRE: ".concat(nombre.toUpperCase()));
        account.setText("N. CUENTA: ".concat(email.toUpperCase()));
        mail.setText("CORREO: ".concat(cuenta.toUpperCase()));
        pass.setText("FACULTAD: ".concat(facultad.toUpperCase()));
        select.setText("CARRERA: ".concat(seleccion.toUpperCase()));

        // Implementar el if-else para la selección de carreras
        if (seleccion.equals("Ingeniería en Computacion")) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.facu_ing_sis);
            frameLayoutDerecho.addView(imageView);
        } else if (seleccion.equals("Medicina")) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.facu_medicina);
            frameLayoutDerecho.addView(imageView);
        } else if (seleccion.equals("Derecho")) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.facu_derecho);
            frameLayoutDerecho.addView(imageView);
        } else if (seleccion.equals("Psicología")) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.facu_medicina);
            frameLayoutDerecho.addView(imageView);
        } else if (seleccion.equals("Ingenieria Industrial")) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.facu_industrial);
            frameLayoutDerecho.addView(imageView);
        } else {
            // Código a ejecutar si no se selecciona ninguna carrera específica
        }

        button.setOnClickListener(v -> {
            if (Img == null) {
                Toast.makeText(getApplicationContext(), "Selecciona una foto de perfil", Toast.LENGTH_LONG).show();
            } else {
                registrar_usuario_respaldo_firebase();
                subirImagenAFirebaseStorage();
                Intent env = new Intent(Tomar_foto.this, enviar_clave_confirmacion.class);
              //  env.putExtra("nombre", String.valueOf(name));
              //  env.putExtra("cuenta", String.valueOf(account));
                startActivity(env);
            }
        });


        Img = findViewById(R.id.ImgPerfil);
        Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarDialogoOpciones();
            }
        });
    }


    private void Permisos() {
        // Metodo para obtener los permisos requeridos de la aplicacion
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, Peticion_AccesoCamara);
        } else {
            dispatchTakePictureIntent();
            //TomarFoto();
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, Peticion_AccesoCamara);
        }
    }

    private void MostrarDialogoOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una opción");
        String[] opciones = {"Tomar foto", "Elegir de la galería"};
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Tomar foto
                    Permisos();
                } else {
                    // Abrir galería
                    AbrirGaleria();
                }
            }
        });
        builder.show();
    }

    private void AbrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Peticion_ElegirGaleria);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Peticion_AccesoCamara) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            {
                Toast.makeText(getApplicationContext(), "Se necesita permiso de la camara", Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Peticion_TomarFoto) {
            try {
                File foto = new File(currentPhotoPath);
                Img.setImageURI(Uri.fromFile(foto));
            } catch (Exception ex) {
                ex.toString();
            }
        } else if (requestCode == Peticion_ElegirGaleria && resultCode == RESULT_OK) {
            // Elegir de la galería
            selectedImage = data.getData();
            Img.setImageURI(selectedImage);
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.toString();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.tarea23pmi.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Peticion_TomarFoto);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use  with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void registrar_usuario_respaldo_firebase() {
        // Respaldo de datos para evitar errores
        if (nombre.isEmpty() || email.isEmpty() || cuenta.isEmpty() || password.isEmpty() || seleccion.isEmpty() || facultad.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Favor, revisar que todos los campos estén llenos", Toast.LENGTH_SHORT).show();

        } else {
            try {
                postUser(nombre, email, cuenta, password, seleccion, facultad);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error al registrar usuario", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void postUser(String name, String cuen, String mail, String passs, String selc, String facu) {
        // Validación del formato del correo electrónico
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(getApplicationContext(), "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return; // salir del método si el correo electrónico no es válido
        }

        // Mapa con datos del usuario
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", name);
        map.put("N.Cuenta", cuen);
        map.put("email", mail);
        map.put("password", passs);
        map.put("carrera", selc);
        map.put("facultad", facu);

        // Agregar usuario a la colección en Firebase
        mfirestore.collection("usuario")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(getApplicationContext(), "Registro completo", Toast.LENGTH_LONG).show();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Registro no ingresado", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void subirImagenAFirebaseStorage() {
        StorageReference filePath = mstorage.child("perfiles").child(selectedImage.getLastPathSegment());

        filePath.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Operación exitosa
                        Toast.makeText(getApplicationContext(), "Registro completo", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el error aquí
                        Toast.makeText(getApplicationContext(), "Error al subir la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        // Puedes usar taskSnapshot para obtener información sobre el progreso
                    }
                });
    }
}
