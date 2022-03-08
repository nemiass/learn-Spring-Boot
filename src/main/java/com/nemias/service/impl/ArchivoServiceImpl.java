package com.nemias.service.impl;

import com.nemias.model.Archivo;
import com.nemias.repo.IArchivoRepo;
import com.nemias.service.IArchivoService;
import javafx.scene.shape.Arc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ArchivoServiceImpl implements IArchivoService {

    @Autowired
    private IArchivoRepo repo;

    @Override public int guardar(Archivo archivo) {
        Archivo ar = repo.save(archivo);
        // devolvemos si el archivo se guardó correctmente, si el id es mayor a cero retorna 1, caso contrario 0
        return ar.getIdArchivo() > 0 ? 1 : 0;
    }

    @Override
    public byte[] leerArchivo(Integer idArchivo) {
        return repo.findById(idArchivo).get().getValue();
    }
}
