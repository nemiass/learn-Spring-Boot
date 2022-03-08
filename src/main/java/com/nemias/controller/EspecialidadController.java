package com.nemias.controller;

import com.nemias.exception.ModelNotFoundException;
import com.nemias.model.Especialidad;
import com.nemias.service.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {

    @Autowired
    private IEspecialidadService service;

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar() {
        List<Especialidad> lista = service.listar();
        return new ResponseEntity<List<Especialidad>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> leerPorId(@PathVariable ("id") Integer id) {
        Optional<Especialidad> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        return new ResponseEntity<Especialidad>(obj.get(), HttpStatus.OK);
    }

    // Example Hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<Especialidad> leerPorIdHateOas(@PathVariable ("id") Integer id) {
        Optional<Especialidad> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        EntityModel<Especialidad> resource = EntityModel.of(obj.get());
        // http://localhost:8080/Especialidads/{id} -> eso es lo que estamos contruyendo en la linea 54
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).leerPorId(id));
        // asociando el link al resource
        resource.add(linkTo.withRel("Especialidades-recursos"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Especialidad obj) {
        Especialidad Especialidad = service.registrar(obj);
        // luego de guardar un nuevo Especialidad, podemos retornar a ese paceinte insertado en el response, pero tambien
        // podríamos devolverle solamente la ruta del endpoint el cual el feontend pueda hacer un request para
        // consultar ese Especialidad, esta url se gurada el header location del reponse, ejemplo:
        // -> construyendo el location que devolveremos en el reponse, que quivaldría a:
        // http://localhost:8080/Especialidads/3, en el caso que de el id fuese 3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Especialidad.getIdEspecialidad())
                .toUri();
        return ResponseEntity.created(location).build();
        // esto se usa dependiendo de los requerimientos, ya aveces va ser necesario devolver el objeto
        // guardado en un json para su comsumo, pero si ese es opcional se devolvería en un el header location
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@Valid @RequestBody Especialidad pac) {
        service.modificar(pac);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable ("id") Integer id) {
        Optional<Especialidad> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        } else {
            service.eliminar(id);
        }
        return new ResponseEntity<Object>(obj.get(), HttpStatus.OK);
    }
}
