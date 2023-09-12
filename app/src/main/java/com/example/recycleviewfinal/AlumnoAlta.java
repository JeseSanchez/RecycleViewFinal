package com.example.recycleviewfinal;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;

import android.net.Uri;

import android.os.Bundle;

import android.provider.MediaStore;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.util.UUID;

import Modelo.AlumnosDb;

public class AlumnoAlta extends AppCompatActivity {

    private Button btnGuardar, btnRegresar, btnEliminar, btnImagen;
    private ItemAlumno alumno;
    private EditText txtNombre, txtMatricula, txtGrado;
    private ImageView imgAlumno;
    private TextView lblUriImagen;
    private int posicion;

    private static final int REQUEST_PERMISSION = 2, REQUEST_CODE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_alta);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnImagen = (Button) findViewById(R.id.btnImagen);
        txtMatricula = (EditText) findViewById(R.id.txtMatricula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtGrado = (EditText) findViewById(R.id.txtGrado);
        imgAlumno = (ImageView) findViewById(R.id.imgAlumno);
        lblUriImagen = (TextView) findViewById(R.id.lblUriImagen);

        Bundle bundle = getIntent().getExtras();
        alumno = (ItemAlumno) bundle.getSerializable("alumno");
        this.posicion = bundle.getInt("posicion", posicion);

        if(posicion >= 0){
            this.txtMatricula.setText(alumno.getTextMatricula());
            this.txtNombre.setText(alumno.getTextNombre());
            this.txtGrado.setText(alumno.getCarrera());

            File imageFile = new File(getFilesDir(), alumno.getImageId() + ".jpg");

            this.imgAlumno.setImageURI(Uri.fromFile(imageFile));
            this.lblUriImagen.setText(alumno.getImageId());
        }

        btnEliminar.setOnClickListener(v -> {
            if(posicion >= 0){
                new AlertDialog.Builder(AlumnoAlta.this).setTitle("Alumno")
                                                               .setMessage(" ¿Desea eliminar al alumno ?")
                                                               .setPositiveButton("Confirmar", (dialog, which) -> {
                                                                    Aplicacion.alumnosDb.deleteAlumno(alumno.getId());
                                                                    Aplicacion.alumnos.remove(posicion);
                                                                    Aplicacion.adaptador.notifyItemRemoved(posicion);
                                                                    Toast.makeText(getApplicationContext(), "Se elimino con exito ",Toast.LENGTH_SHORT).show();
                                                                    setResult(Activity.RESULT_OK);
                                                                    finish();
                                                                    Aplicacion.adaptador.notifyDataSetChanged();
                                                               })
                                                               .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                                                               .show();
            }
        });

        btnGuardar.setOnClickListener(v -> {

            if(alumno == null){

                alumno = new ItemAlumno();
                alumno.setCarrera(txtGrado.getText().toString());
                alumno.setTextMatricula(txtMatricula.getText().toString());
                alumno.setTextNombre(txtNombre.getText().toString());
                alumno.setImageId(lblUriImagen.getText().toString());

                if(!(txtNombre.getText().toString().equals("")    ||
                     txtMatricula.getText().toString().equals("") ||
                     txtGrado.getText().toString().equals("")     ||
                     lblUriImagen.getText().toString().equals(""))){
                    AlumnosDb alumnosDb = new AlumnosDb(getApplicationContext());

                    alumnosDb.insertAlumno(alumno);
                    Aplicacion.alumnos.add(alumno);

                    Toast.makeText(getApplicationContext(),
                              "Se inserto un alumno ",
                                   Toast.LENGTH_SHORT).show();

                    setResult(Activity.RESULT_OK);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                               "Falto capturar datos ",
                                   Toast.LENGTH_SHORT).show();
                }
            }

            if(posicion >= 0){
                alumno.setTextMatricula(txtMatricula.getText().toString());
                alumno.setTextNombre(txtNombre.getText().toString());
                alumno.setCarrera(txtGrado.getText().toString());
                alumno.setImageId(lblUriImagen.getText().toString());

                Aplicacion.alumnosDb.updateAlumno(alumno);

                Aplicacion.alumnos.get(posicion).setTextMatricula(alumno.getTextMatricula());
                Aplicacion.alumnos.get(posicion).setTextNombre(alumno.getTextNombre());
                Aplicacion.alumnos.get(posicion).setCarrera(alumno.getCarrera());
                Aplicacion.alumnos.get(posicion).setImageId(alumno.getImageId());

                Toast.makeText(getApplicationContext(), "Se modifico con exito ",Toast.LENGTH_SHORT).show();

                setResult(Activity.RESULT_OK);
            }
            finish();
        });

        btnRegresar.setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        btnImagen.setOnClickListener(v -> cargarImagen());

    }

    private void cargarImagen() {
        if(ContextCompat.checkSelfPermission(AlumnoAlta.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(AlumnoAlta.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);

        }
        else{
            abrirGaleria();
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirGaleria();
            }
            else{
                Toast.makeText(AlumnoAlta.this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri selectedImageUri = data.getData();

            String imageId = UUID.randomUUID().toString();

            lblUriImagen.setText(imageId);

            File destinationFile = new File(getFilesDir(), imageId + ".jpg");
            try {
                copyImageFile(selectedImageUri, destinationFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imgAlumno.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void copyImageFile(Uri sourceUri, File destinationFile) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try (InputStream inputStream = getContentResolver().openInputStream(sourceUri); OutputStream outputStream = Files.newOutputStream(destinationFile.toPath())) {
                byte[] buffer = new byte[2048];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
    }
}


