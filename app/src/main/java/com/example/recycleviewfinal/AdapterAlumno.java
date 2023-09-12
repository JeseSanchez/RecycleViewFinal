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
    private ArrayList<ItemAlumno> listaAlumno;

    private View.OnClickListener listener;
    private Context context;
    private LayoutInflater inflater;

    public AdapterAlumno(ArrayList<ItemAlumno> listaAlumno, Context context){
        this.listaAlumno = listaAlumno ;
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
        private TextView txtNombre, txtMatricula, txtCarrera;
        private ImageView idImagen;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            txtMatricula = (TextView) itemView.findViewById(R.id.lblMatricula);
            txtCarrera = (TextView) itemView.findViewById(R.id.lblCarrera);

            idImagen = (ImageView) itemView.findViewById(R.id.imgAlumno);
        }
    }

    public Filter getFilter(){
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<ItemAlumno> listaFiltrada = new ArrayList<>();

                if(constraint == null || constraint.length() == 0){
                    listaFiltrada.addAll(listaAlumno);

                }else {
                    String searchStr = constraint.toString().toLowerCase().trim();

                    for (ItemAlumno itemAlumno : listaAlumno) {

                        if (itemAlumno.getTextNombre().toLowerCase().contains(searchStr)) {
                            listaFiltrada.add(itemAlumno);
                        }
                    }
                    filterResults.values = listaFiltrada;
                    filterResults.count = listaFiltrada.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaAlumno.clear();
                if (results.count > 0){
                    listaAlumno.addAll((ArrayList<ItemAlumno>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }
}

