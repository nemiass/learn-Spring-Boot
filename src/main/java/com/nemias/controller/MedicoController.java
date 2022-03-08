package com.nemias.controller;

import com.nemias.exception.ModelNotFoundException;
import com.nemias.model.Medico;
import com.nemias.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private IMedicoService service;

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        List<Medico> lista = service.listar();
        return new ResponseEntity<List<Medico>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> leerPorId(@PathVariable ("id") Integer id) {
        Optional<Medico> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        return new ResponseEntity<Medico>(obj.get(), HttpStatus.OK);
    }

    // Example Hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<Medico> leerPorIdHateOas(@PathVariable ("id") Integer id) {
        Optional<Medico> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        EntityModel<Medico> resource = EntityModel.of(obj.get());
        // http://localhost:8080/Medicos/{id} -> eso es lo que estamos contruyendo en la linea 54
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).leerPorId(id));
        // asociando el link al resource
        resource.add(linkTo.withRel("Medicos-recursos"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Medico pac) {
        Medico Medico = service.registrar(pac);
        // luego de guardar un nuevo Medico, podemos retornar a ese paceinte insertado en el response, pero tambien
        // podríamos devolverle solamente la ruta del endpoint el cual el feontend pueda hacer un request para
        // consultar ese Medico, esta url se gurada el header location del reponse, ejemplo:
        // -> construyendo el location que devolveremos en el reponse, que quivaldría a:
        // http://localhost:8080/Medicos/3, en el caso que de el id fuese 3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Medico.getIdMedico())
                .toUri();
        return ResponseEntity.created(location).build();
        // esto se usa dependiendo de los requerimientos, ya aveces va ser necesario devolver el objeto
        // guardado en un json para su comsumo, pero si ese es opcional se devolvería en un el header location
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@Valid @RequestBody Medico pac) {
        service.modificar(pac);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable ("id") Integer id) {
        Optional<Medico> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        } else {
            service.eliminar(id);
        }
        return new ResponseEntity<Object>(obj.get(), HttpStatus.OK);
    }
}
