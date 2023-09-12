package Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlumnosDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMA_SEP = " ,";

    private static final String SQL_CREATE_ALUMNO = "CREATE TABLE " +
            DefineTabla.Alumnos.TABLE_NAME + " (" +
            DefineTabla.Alumnos.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
            DefineTabla.Alumnos.COLUMN_NAME_MATRICULA + TEXT_TYPE + COMA_SEP +
            DefineTabla.Alumnos.COLUMN_NAME_NOMBRE + TEXT_TYPE + COMA_SEP +
            DefineTabla.Alumnos.COLUMN_NAME_CARRERA + TEXT_TYPE + COMA_SEP +
            DefineTabla.Alumnos.COLUMN_NAME_FOTO + TEXT_TYPE + ")";

    private static final String SQL_DELETE_ALUMNO = "DROP TABLE IF EXISTS " +
            DefineTabla.Alumnos.TABLE_NAME;

    //Cambiar el nombre cuando actualice la estructura de la tabla
    //Cambiar el nombre cuando actualice la estructura de la tabla
    private static final String DATABASE_NAME = "sistema8.db";
    private static final   int DATABASE_VERCION = 1;

    public AlumnosDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERCION);
    }

    //FUNCION PARA CRAR LA BASE DE DATOS
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ALUMNO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {
        sqLiteDatabase.execSQL(SQL_DELETE_ALUMNO);
        onCreate(sqLiteDatabase);
    }
}


