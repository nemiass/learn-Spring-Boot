package com.nemias.repo;

import com.nemias.model.ConsultaExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface IConsultaExamenRepo extends JpaRepository<ConsultaExamen, Integer> {

    //@Transactional // esto colocamos para que se confirmen los cambios que hacemos con el isnert
    @Modifying // colocamos para indicarle a jpa que esta consuñta no va retornar nada, solo que var ser una
    // consulta de cambios, ademas al Modifying le debemos acompañar de la notacion transaccional, pero el
    // transaccional tambien puede estar en el service, para asi tener un bloque transactional
    @Query(value = "INSERT INTO consulta_examen(id_consulta, id_examen) VALUES(:idConsulta, :idExamen)",
            nativeQuery = true)
    Integer registrar(@Param("idConsulta") Integer idConsulta, @Param("idExamen") Integer idExamen);

    // Query para traer examenes por una consulta determinada
    @Query("from ConsultaExamen ce where ce.consulta.IdConsulta=:idConsulta")
    List<ConsultaExamen> listarExamenesPorConsulta(@Param("idConsulta") Integer idConsulta);
}
