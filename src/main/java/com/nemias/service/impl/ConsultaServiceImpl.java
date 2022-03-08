package com.nemias.service.impl;

import com.nemias.dto.ConsultaListaExamenDTO;
import com.nemias.dto.ConsultaResumenDTO;
import com.nemias.dto.FiltroConsultaDTO;
import com.nemias.model.Consulta;
import com.nemias.repo.IConsultaExamenRepo;
import com.nemias.repo.IConsultaRepo;
import com.nemias.service.IConsultaService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    @Autowired
    private IConsultaRepo repo;

    @Autowired
    private IConsultaExamenRepo ceRepo;

    @Transactional // le indicamos que esto va ser una transaccion, osea que si una consulta a ba falla,
    // se detenga todo, en teoria si falla algo hace un roolback
    @Override
    public Consulta registrarTransaccional(ConsultaListaExamenDTO consultaDTO) {
        consultaDTO.getConsulta().getDetalleConsulta().forEach(det -> {
            det.setConsulta(consultaDTO.getConsulta());
        });
        // al hacer el save, le estaos pasando la consulta, asi que cunado este registra coloca datos a la consulta
        // como por ejemplo el id, que el cual nosotros accedemos a ella despues, ademas a save le pasamos la
        // referencia del objeto y este lo rellena con los datos como por ejemplo el "id" cuando registramos
        repo.save(consultaDTO.getConsulta());
        consultaDTO.getListExamen().forEach(examen -> {
            ceRepo.registrar(consultaDTO.getConsulta().getIdConsulta(), examen.getIdExamen());
        });
        return consultaDTO.getConsulta();
    }

    @Override
    public Consulta registrar(Consulta obj) {
        // al recibir una consulta, recorremos lso detalles consultas, y le agregamos la consulta
        // que este necesita como referencia.
        obj.getDetalleConsulta().forEach(detalleConsulta -> {
            detalleConsulta.setConsulta(obj);
        });
        return repo.save(obj);
    }

    @Override
    public Consulta modificar(Consulta obj) {
        return repo.save(obj);
    }

    @Override
    public List<Consulta> listar() {
        return repo.findAll();
    }

    @Override
    public Optional<Consulta> leerPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<Consulta> buscar(FiltroConsultaDTO filtro) {
        return repo.buscar(filtro.getDni(), filtro.getNombreCompleto());
    }

    @Override
    public List<Consulta> buscarFecha(FiltroConsultaDTO filtro) {
        LocalDateTime fechaSiguiente = filtro.getFechaConsulta().plusDays(1);
        return repo.buscarFecha(filtro.getFechaConsulta(), fechaSiguiente);
    }

    @Override
    public List<ConsultaResumenDTO> listarResumen() {
        List<ConsultaResumenDTO> consultas = new ArrayList<>();
        // List<Object[]> -> es una lista que tiene un array de objects, osea ese array puede tener todo tipo de datos
        // cantidad de fecha
        // [4, 11/05/2019]
        // [1, 18/05/2019]
        repo.listarResumen().forEach(arr -> {
            ConsultaResumenDTO consultaResumenDTO = new ConsultaResumenDTO();
            consultaResumenDTO.setCantidad(Integer.parseInt(String.valueOf(arr[0])));
            consultaResumenDTO.setFecha(String.valueOf(arr[1]));
            consultas.add(consultaResumenDTO);
        });
        return consultas;
    }

    // implementando nuestra metodo para generar nuestro reporte
    @Override
    public byte[] generarReporte() {
        // Para manejar la excepcion de los archivos es recomendable usar un bloque try Catch
        // el objetivo de hacer esto es que, este servicio retorne el un pdf en bytes para que el frontend pueda
        // consumirlo y reconstruirlo
        // variable en donde se guardará el reporte pdf en bytes
        byte[] data = null;
        try {
            // obteniendo nuestro archivo jasepr
            File file = new ClassPathResource("/reports/reporte.jasper").getFile();
            // le pasamos el path de nuestro archivo jasper, como segundo parametro le pasamos null, como segundo
            // parametro va un hsahmap con clave valor, y estos se colocan en un "bloque de parametros" que es un tipo
            // de dato que se coloca en el reporte con el programa "jasperSoft", pero le pasamos null ya que solo tenemos
            // parametros tipo field que son iterables y no bloque de parametros: ejemplo de hashmap que va
            // HashMap<String, String> params = new HashMap<>();
            // params.put("txt_empresa", "Mi empresa xd");
            // * Como 3er parametro le pasamos nuestro datos que colocaran en nuestros field que pusimos con "japerSoft",
            // le pasamos nuestro DTO, pero lo envolvemos en un JRBeanCollectionDataSource donde le pasamos nuestro metodo
            // que nos devuelve nuestro dto.
            JasperPrint print = JasperFillManager.fillReport(file.getPath(), null,
                    new JRBeanCollectionDataSource(this.listarResumen()));
            // exportamos el nuestro reporte a pdf y lo guarda como arreglo de bytes
            data = JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            // ejemplo de lanzando nuestra excepcion personalizada, si es que lo creamos
            //throw new MiExcepcionPersonalizada("error con el archivo");
            e.printStackTrace();
        }
        return data;
    }
}
