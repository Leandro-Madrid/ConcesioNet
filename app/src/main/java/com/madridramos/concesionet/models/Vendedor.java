package com.madridramos.concesionet.models;


import java.io.Serializable;

public class Vendedor implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String password;

    // Constructor vacío (por defecto)
    public Vendedor() {
    }

    // Constructor para crear un nuevo vendedor sin ID (para inserción en la base de datos)
    public Vendedor(String nombre, String apellido, String telefono, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.password = password;
    }

    // Constructor completo para un vendedor existente (para recuperación desde la base de datos)
    public Vendedor(int id, String nombre, String apellido, String telefono, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.password = password;
    }

    // Getters y setters para todos los campos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
