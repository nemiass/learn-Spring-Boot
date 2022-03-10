package com.nemias.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemias.model.Menu;
import com.nemias.repo.IMenuRepo;
import com.nemias.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private IMenuRepo repo;

    @Override
    public Menu registrar(Menu menu) {
        return repo.save(menu);
    }

    @Override
    public Menu modificar(Menu menu) {
        return repo.save(menu);
    }

    @Override
    public void eliminar(Integer idMenu) {
        repo.deleteById(idMenu);
    }

    @Override
    public Optional<Menu> leerPorId(Integer idMenu) {
        return repo.findById(idMenu);
    }

    @Override
    public List<Menu> listar() {
        return repo.findAll();
    }

    @Override
    public List<Menu> listarMenuPorUsuario(String nombre) {
        List<Menu> menus = new ArrayList<>();

        repo.listarMenuPorUsuario(nombre).forEach( x -> {
            Menu m = new Menu();
            m.setIdMenu((Integer.parseInt(String.valueOf(x[0]))));
            m.setIcono(String.valueOf(x[1]));
            m.setNombre(String.valueOf(x[2]));
            m.setUrl(String.valueOf(x[3]));
            System.out.println(x[2]);
            menus.add(m);
        });
        return menus;
    }
}
