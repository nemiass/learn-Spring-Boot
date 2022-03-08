package com.nemias.controller;

import com.nemias.dto.ConsultaListaExamenDTO;
import com.nemias.dto.ConsultaResumenDTO;
import com.nemias.dto.FiltroConsultaDTO;
import com.nemias.exception.ModelNotFoundException;
import com.nemias.model.Archivo;
import com.nemias.model.Consulta;
import com.nemias.service.IArchivoService;
import com.nemias.service.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private IConsultaService service;

    // llamanod a nuestro service de archivo, para mostrar la imagen
    @Autowired
    private IArchivoService archivoService;

    @GetMapping
    public ResponseEntity<List<Consulta>> listar() {
        List<Consulta> lista = service.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> leerPorId(@PathVariable ("id") Integer id) {
        Optional<Consulta> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        return new ResponseEntity<>(obj.get(), HttpStatus.OK);
    }

    // Example Hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<Consulta> leerPorIdHateOas(@PathVariable ("id") Integer id) {
        Optional<Consulta> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        EntityModel<Consulta> resource = EntityModel.of(obj.get());
        // http://localhost:8080/Consultas/{id} -> eso es lo que estamos contruyendo en la linea 54
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).leerPorId(id));
        // asociando el link al resource
        resource.add(linkTo.withRel("Consultaes-recursos"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody ConsultaListaExamenDTO obj) {
        Consulta Consulta = service.registrarTransaccional(obj);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Consulta.getIdConsulta())
                .toUri();
        return ResponseEntity.created(location).build();
        // esto se usa dependiendo de los requerimientos, ya aveces va ser necesario devolver el objeto
        // guardado en un json para su comsumo, pero si ese es opcional se devolvería en un el header location
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@Valid @RequestBody Consulta pac) {
        service.modificar(pac);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable ("id") Integer id) {
        Optional<Consulta> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        } else {
            service.eliminar(id);
        }
        return new ResponseEntity<>(obj.get(), HttpStatus.OK);
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<Consulta>> bucar(@RequestBody FiltroConsultaDTO filtro) {
        List<Consulta> consultas = new ArrayList<>();

        if (filtro != null) {
            if (filtro.getFechaConsulta() != null) {
                consultas = service.buscarFecha(filtro);
            } else {
                consultas = service.buscar(filtro);
            }
        }
        System.out.println(consultas);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/listarResumen")
    public ResponseEntity<List<ConsultaResumenDTO>> listarResumen() {
        List<ConsultaResumenDTO> lsConsultaDTO = service.listarResumen();
        return new ResponseEntity<>(lsConsultaDTO, HttpStatus.OK);
    }

    // En nuestro Endpoint le indicamos que vamos a devolver un arreglo de bytes
    @GetMapping(value = "/generarReporte", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generarReporte() {
        byte[] data;
        data = service.generarReporte();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // Endpoint para guardar un archivo, osea este archivo lo recibimos desde el frontend, recordar que lo estamos
    // guardando la imagen en byte de arrays en la BD, y no en en una carpeta
    @PostMapping(value = "/guardarArchivo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Integer> guardarArchivo(@RequestParam("file") MultipartFile file) throws IOException {
        int rpta = 0;
        // creamos nuestro objeto archivo, el cual es de nuestro modelo
        Archivo archivo = new Archivo();
        // setemoas los datos de nuestro archivo, haciendo uso del file que nos llega desde el frontend
        archivo.setFileName(file.getOriginalFilename());
        archivo.setValue(file.getBytes());
        archivo.setFileType(file.getContentType());
        // guardamos ela rchivo
        rpta = archivoService.guardar(archivo);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    // endpoint para leer archivo, el cual le enviaremos un arreglo de bytes al frontend
    @GetMapping(value = "/leerArchivo/{idArchivo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> leerArchivo(@PathVariable("idArchivo") Integer idArchivo) throws IOException {
        byte[] arr = archivoService.leerArchivo(idArchivo);
        return new ResponseEntity<>(arr, HttpStatus.OK);
    }
}
