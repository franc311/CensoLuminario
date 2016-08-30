package com.franzhezco.censoluminario;

/**
 * Created by Ryner on 19/07/2016.
 */
public class Punto {
    private int id;
    private int idCenso;
    private String serie;
    private double latitude;
    private double longitude;

    public Punto() {

    }

    public Punto(int idCenso, String serie, double latitude, double longitude) {
        this.idCenso = idCenso;
        this.serie = serie;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Punto(int id, int idCenso ,String serie, double latitude, double longitude) {
        this.id = id;
        this.idCenso = idCenso;
        this.serie = serie;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIdCenso(int idCenso) {
        this.idCenso = idCenso;
    }

    public int getIdCenso() {
        return idCenso;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getSerie() {
        return serie;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }
}