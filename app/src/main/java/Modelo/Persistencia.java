package Modelo;

import com.example.recycleviewfinal.ItemAlumno;

public interface Persistencia {

    public void openDataBase();
    public void closeDataBase();
    public long insertAlumno(ItemAlumno alumno);
    public long updateAlumno(ItemAlumno alumno);
    public void deleteAlumno(int id);

}
