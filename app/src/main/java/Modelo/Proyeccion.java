package Modelo;


import android.database.Cursor;
import com.example.recycleviewfinal.ItemAlumno;
import java.util.ArrayList;

public interface Proyeccion {

    public ItemAlumno getAlumno(String matricula);
    public ArrayList<ItemAlumno> allAlumnos();
    public ItemAlumno readAlumno(Cursor cursor);
}
