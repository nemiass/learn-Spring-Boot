package com.nemias.controller;

import com.nemias.exception.ModelNotFoundException;
import com.nemias.model.Paciente;
import com.nemias.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

// importando estaticamente
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteService service;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> lista = service.listar();
        return new ResponseEntity<List<Paciente>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> leerPorId(@PathVariable ("id") Integer id) {
        Optional<Paciente> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        return new ResponseEntity<Paciente>(obj.get(), HttpStatus.OK);
    }

    // Example Hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<Paciente> leerPorIdHateOas(@PathVariable ("id") Integer id) {
        Optional<Paciente> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        }
        EntityModel<Paciente> resource = EntityModel.of(obj.get());
        // http://localhost:8080/pacientes/{id} -> eso es lo que estamos contruyendo en la linea 54
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).leerPorId(id));
        // asociando el link al resource
        resource.add(linkTo.withRel("pacientes-recursos"));
        return resource;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Paciente pac) {
        Paciente paciente = service.registrar(pac);
        // luego de guardar un nuevo paciente, podemos retornar a ese paceinte insertado en el response, pero tambien
        // podr??amos devolverle solamente la ruta del endpoint el cual el feontend pueda hacer un request para
        // consultar ese paciente, esta url se gurada el header location del reponse, ejemplo:
        // -> construyendo el location que devolveremos en el reponse, que quivaldr??a a:
        // http://localhost:8080/pacientes/3, en el caso que de el id fuese 3
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paciente.getIdPaciente())
                .toUri();
        return ResponseEntity.created(location).build();
        // esto se usa dependiendo de los requerimientos, ya aveces va ser necesario devolver el objeto
        // guardado en un json para su comsumo, pero si ese es opcional se devolver??a en un el header location
    }

    @PutMapping
    public ResponseEntity<Object> modificar(@Valid @RequestBody Paciente pac) {
        service.modificar(pac);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable ("id") Integer id) {
        Optional<Paciente> obj =  service.leerPorId(id);
        if (!obj.isPresent()) {
            throw new ModelNotFoundException("ID No encontrado " + id);
        } else {
            service.eliminar(id);
        }
        return new ResponseEntity<Object>(obj.get(), HttpStatus.OK);
    }

    // IMplementando nuestro endpoint para hacer el pageable, osea para listar paginadamente, osea
    // no se retorna una lista de pacientes, sino una pagina de pacientes
    @GetMapping("/pageable")
    public ResponseEntity<Page<Paciente>> listarPageable(Pageable pageable) {
        Page<Paciente> pacientes = service.listarPageable(pageable);
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }
}
