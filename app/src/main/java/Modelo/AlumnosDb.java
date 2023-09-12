package Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.recycleviewfinal.ItemAlumno;

import java.util.ArrayList;

public class AlumnosDb implements Persistencia, Proyeccion{

    private Context context;
    private AlumnosDbHelper helper;
    private SQLiteDatabase db;

    public AlumnosDb(Context context, AlumnosDbHelper helper){
        this.context = context;
        this.helper = helper;
    }

    public AlumnosDb(Context context){
        this.context = context;
        this.helper = new AlumnosDbHelper(this.context);
    }


    @Override
    public void openDataBase() {
        db = helper.getWritableDatabase();
    }

    @Override
    public void closeDataBase() {
        helper.close();
    }

    @Override
    public long insertAlumno(ItemAlumno alumno) {

        ContentValues values = new ContentValues();

        values.put(DefineTabla.Alumnos.COLUMN_NAME_MATRICULA, alumno.getTextMatricula());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_NOMBRE, alumno.getTextNombre());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_CARRERA, alumno.getCarrera());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_FOTO, alumno.getImageId());

        this.openDataBase();
        long num = db.insert(DefineTabla.Alumnos.TABLE_NAME, null, values);
        //this.closeDataBase();
        Log.d("agregar", "insertAlumno" + num);

        return num;
    }

    @Override
    public long updateAlumno(ItemAlumno alumno) {
        ContentValues values = new ContentValues();

        values.put(DefineTabla.Alumnos.COLUMN_NAME_ID, alumno.getId());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_MATRICULA, alumno.getTextMatricula());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_NOMBRE, alumno.getTextNombre());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_CARRERA, alumno.getCarrera());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_FOTO, alumno.getImageId());

        this.openDataBase();
        long num = db.update(
                DefineTabla.Alumnos.TABLE_NAME,
                values,
                DefineTabla.Alumnos.COLUMN_NAME_ID + " = " + alumno.getId(),
                null);

        //this.closeDataBase();

        return num;
    }

    @Override
    public void deleteAlumno(int id) {
        this.openDataBase();
        db.delete(
                DefineTabla.Alumnos.TABLE_NAME,
                DefineTabla.Alumnos.COLUMN_NAME_ID + "=?",
                new String[] {String.valueOf(id)});

        //this.closeDataBase();
    }


    @Override
    public ItemAlumno getAlumno(String matricula) {

        db = helper.getWritableDatabase();

        Cursor cursor = db.query(
                DefineTabla.Alumnos.TABLE_NAME,
                DefineTabla.REGISTRO,
                DefineTabla.Alumnos.COLUMN_NAME_ID + " = ?",
                new String[] {matricula},
                null, null, null);

        cursor.moveToFirst();
        ItemAlumno alumno = readAlumno(cursor);

        return alumno;
    }

    @Override
    public ArrayList<ItemAlumno> allAlumnos() {
        db = helper.getWritableDatabase();

        Cursor cursor = db.query(
                DefineTabla.Alumnos.TABLE_NAME,
                DefineTabla.REGISTRO,
                null,null,null, null, null);

        ArrayList<ItemAlumno> alumnos = new ArrayList<ItemAlumno>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            ItemAlumno alumno = readAlumno(cursor);
            alumnos.add(alumno);
            cursor.moveToNext();
        }

        cursor.close();
        return alumnos;
    }

    @Override
    public ItemAlumno readAlumno(Cursor cursor) {
        ItemAlumno alumno = new ItemAlumno();

        alumno.setId(cursor.getInt(0));
        alumno.setTextMatricula(cursor.getString(1));
        alumno.setTextNombre(cursor.getString(2));
        alumno.setCarrera(cursor.getString(3));
        alumno.setImageId(cursor.getString(4));

        return alumno;
    }
}
