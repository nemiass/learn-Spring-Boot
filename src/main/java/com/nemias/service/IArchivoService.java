package com.nemias.service;

import com.nemias.model.Archivo;

public interface IArchivoService {

    int guardar(Archivo archivo);
    byte[] leerArchivo(Integer idArchivo);
}
