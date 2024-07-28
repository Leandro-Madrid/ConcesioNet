package com.madridramos.concesionet.models;

public class Auto {
    private int idAuto;
    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private int idVendedor; // id del vendedor asociado

    // Constructor para un nuevo auto sin idAuto (para inserción en base de datos)
    public Auto(String marca, String modelo, int anio, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.idVendedor = -1; // Valor por defecto para indicar que no hay vendedor asociado
    }

    // Constructor para un auto existente con idAuto (para recuperación desde la base de datos)
    public Auto(int idAuto, String marca, String modelo, int anio, double precio, int idVendedor) {
        this.idAuto = idAuto;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.idVendedor = idVendedor;
    }

    // Constructor para un auto existente con idAuto y idVendedor (para recuperación desde la base de datos)
    public Auto(int idAuto, int idVendedor) {
        this.idAuto = idAuto;
        this.idVendedor = idVendedor;
        // Los demás campos quedan inicializados con valores por defecto
        this.marca = null;
        this.modelo = null;
        this.anio = 0;
        this.precio = 0.0;
    }

    public Auto() {

    }

    // Getters y setters para todos los campos

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }
}
