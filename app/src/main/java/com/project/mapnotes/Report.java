package com.project.mapnotes;

public class Report {

    private String title;
    private String nota;
    private double latitud;
    private double longitud;
    private String imagePath;
    private String videoPath;
    private String audioPath;

    public Report() {
    }

    public Report(String title, String nota, double latitud, double longitud, String imagePath, String videoPath, String audioPath) {
        this.title = title;
        this.nota = nota;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagePath = imagePath;
        this.videoPath = videoPath;
        this.audioPath = audioPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
}


