package com.example.recycleviewfinal;

import android.app.Application;

import java.util.ArrayList;

import Modelo.AlumnosDb;

public class Aplicacion extends Application {

    static ArrayList<ItemAlumno> alumnos;
    static AdapterAlumno adaptador;

    public ArrayList<ItemAlumno> getAlumnos(){ return alumnos; }

    public AdapterAlumno getAdaptador(){ return adaptador; }

    static AlumnosDb alumnosDb;

    @Override
    public void onCreate(){
        super.onCreate();
        alumnosDb = new AlumnosDb(getApplicationContext());
        alumnos = alumnosDb.allAlumnos();
        alumnosDb.openDataBase();

        adaptador = new AdapterAlumno(alumnos, this);
    }

}