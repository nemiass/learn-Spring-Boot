package com.nemias.service;

import com.nemias.model.Menu;

import java.util.List;

public interface IMenuService extends ICRUD<Menu> {

    List<Menu> listarMenuPorUsuario(String nombre);
}
