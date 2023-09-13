package com.example.recycleviewfinal;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class AdapterAlumno extends RecyclerView.Adapter<AdapterAlumno.ViewHolder> implements View.OnClickListener{
    private ArrayList<ItemAlumno> listaAlumno,
    listaOriginal;

    private View.OnClickListener listener;
    private Context context;
    private LayoutInflater inflater;

    public AdapterAlumno(ArrayList<ItemAlumno> listaAlumno, Context context){
        this.listaOriginal = new ArrayList<>(Aplicacion.alumnosDb.allAlumnos());
        this.listaAlumno = listaAlumno;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public AdapterAlumno.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemalumno, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterAlumno.ViewHolder holder, int position) {
        ItemAlumno alumno = this.listaAlumno.get(position);
        holder.txtMatricula.setText(alumno.getTextMatricula());
        holder.txtNombre.setText(alumno.getTextNombre());
        holder.txtCarrera.setText(alumno.getCarrera());

        File directorioArchivos = (context).getFilesDir();

        File imageFile = new File(directorioArchivos, alumno.getImageId() + ".jpg");


        holder.idImagen.setImageURI(Uri.fromFile(imageFile));
    }

    @Override
    public int getItemCount() {
        return this.listaAlumno.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){this.listener = listener;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNombre,
                         txtMatricula,
                         txtCarrera;
        private ImageView idImagen;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtNombre = itemView.findViewById(R.id.lblNombre);
            txtMatricula = itemView.findViewById(R.id.lblMatricula);
            txtCarrera = itemView.findViewById(R.id.lblCarrera);

            idImagen = itemView.findViewById(R.id.imgAlumno);
        }
    }

    public void buscar(String cadena){
        ArrayList<ItemAlumno> listaFiltrada = new ArrayList<>();
        for (ItemAlumno itemAlumno : listaAlumno) {

            if (itemAlumno.getTextNombre().toLowerCase().contains(cadena.toLowerCase())) {
                listaFiltrada.add(itemAlumno);
            }
        }
        listaAlumno = listaFiltrada;
        notifyDataSetChanged();
    }
    public void desBuscar(){
        listaAlumno = listaOriginal;
        notifyDataSetChanged();
    }
}

