package com.nemias.repo;

import com.nemias.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IConsultaRepo extends JpaRepository<Consulta, Integer> {

    // JPQL examples
    @Query("FROM Consulta c WHERE c.paciente.dni=:dni OR LOWER(c.paciente.nombres) LIKE %:nombreCompleto% OR LOWER(c.paciente.apellidos) LIKE %:nombreCompleto%")
    List<Consulta> buscar(@Param("dni") String dni, @Param("nombreCompleto") String nombreCompleto);

    @Query("from Consulta c where c.fecha between :fechaConsulta and :fechaSiguiente")
    List<Consulta> buscarFecha(@Param("fechaConsulta") LocalDateTime fechaConsulta,
            @Param("fechaSiguiente") LocalDateTime fechaSiguiente);

    // cuando realiazmos este tipo de querys nativos, siempre nos devuelven un arerglo de objeto, osea un
    // objeto que tiene un arreglo
    @Query(value = "select * from fn_listarResumen()", nativeQuery = true)
    List<Object[]> listarResumen();
}
