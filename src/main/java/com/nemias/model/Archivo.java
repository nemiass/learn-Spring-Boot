package com.nemias.model;

import javax.persistence.*;

@Entity
@Table(name = "archivo")
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idArchivo;

    private String fileName;
    private String fileType;
    private byte[] value;

    public int getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
