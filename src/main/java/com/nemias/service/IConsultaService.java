package com.nemias.service;

import com.nemias.dto.ConsultaListaExamenDTO;
import com.nemias.dto.ConsultaResumenDTO;
import com.nemias.dto.FiltroConsultaDTO;
import com.nemias.model.Consulta;

import java.util.List;

public interface IConsultaService extends ICRUD<Consulta>{
    // agregando una consulta personalizada transaccional
    Consulta registrarTransaccional(ConsultaListaExamenDTO consultaDTO);

    List<Consulta> buscar(FiltroConsultaDTO filtro);

    List<Consulta> buscarFecha(FiltroConsultaDTO filtro);

    // creando nuestro metodo que ejeuctará nuestro procedure, y retornará nuestro DTO
    List<ConsultaResumenDTO> listarResumen();

    // Este será nuestro metodo el cual nos generará nuestro reporte como un arreglo de bytes
    byte[] generarReporte();
}
