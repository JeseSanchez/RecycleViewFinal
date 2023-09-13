package com.example.recycleviewfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private FloatingActionButton fbtnAgregar,
                                 fbtnRegresar;

    private ItemAlumno alumno;
    private int posicion = -1;

    private Aplicacion app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (Aplicacion) getApplication();
        recyclerView = findViewById(R.id.recId);
        recyclerView.setAdapter(app.getAdaptador());
        fbtnAgregar = findViewById(R.id.agregarAlumno);
        fbtnRegresar = findViewById(R.id.Regresar);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        app.getAdaptador().setOnClickListener(v -> {
            int posicion = recyclerView.getChildAdapterPosition(v);
            alumno = app.getAlumnos().get(posicion);

            Intent intent = new Intent(MainActivity.this, AlumnoAlta.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("alumno", alumno);
            intent.putExtra("posicion", posicion);
            intent.putExtras(bundle);

            startActivityForResult(intent, 1);
        });

        fbtnAgregar.setOnClickListener(v -> {
            alumno = null;
            Intent intent = new Intent(MainActivity.this, AlumnoAlta.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("alumno", alumno);
            bundle.putInt("posicion", posicion);
            intent.putExtras(bundle);

            startActivityForResult(intent, 0);
        });

        fbtnRegresar.setOnClickListener(v -> {
                    new AlertDialog.Builder(this).setTitle("Lista Alumnos")
                            .setMessage(" ¿Desea salir? ")
                            .setPositiveButton("Confirmar", (dialog, which) -> finishAndRemoveTask())
                            .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                            .show();
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        recyclerView.getAdapter().notifyDataSetChanged();
        posicion = -1;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchview, menu);

        Aplicacion aplicacion = (Aplicacion) getApplication();

        MenuItem menuItem = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                aplicacion.getAdaptador().buscar(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            aplicacion.getAdaptador().desBuscar();
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }
}