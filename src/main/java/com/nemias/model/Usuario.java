package com.nemias.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    private int idUsuario;

    @Column(name = "nombre", nullable = false, unique = true)
    private String username;

    @Column(name = "contrasenia", nullable = false)
    private String password;

    @Column(name = "estado", nullable = false)
    protected boolean enabled;

    // Creando la relacion de muchos a muchos con la table de Rols
    @ManyToMany(fetch = FetchType.EAGER)
    // con name indicamos la tabla intermedia que se va crear, en joinColumnas le pasamos e nombre que va tener el
    // campo en BD ya tambien a quien hace referencia, que en este caso es "idUsuario", luego hacemos lo mismo
    // en la tabla inversa que sería Rol, le indicamos el nombre que va tener en la tabla y a quien hace referencia,
    // que en este caso es "idRol"
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "idUsuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
    private List<Rol> roles;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
