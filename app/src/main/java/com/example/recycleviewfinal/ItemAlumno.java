package com.example.recycleviewfinal;

import java.io.Serializable;

public class ItemAlumno implements Serializable {

    private int id;
    private String carrera, textNombre, textMatricula, imageId;

    public ItemAlumno() {
        this.carrera = "";
        this.textNombre = "";
        this.textMatricula = "";
        this.imageId = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public String getTextNombre() {
        return textNombre;
    }

    public void setTextNombre(String textNombre) {
        this.textNombre = textNombre;
    }

    public String getTextMatricula() {
        return textMatricula;
    }

    public void setTextMatricula(String textMatricula) {
        this.textMatricula = textMatricula;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

}