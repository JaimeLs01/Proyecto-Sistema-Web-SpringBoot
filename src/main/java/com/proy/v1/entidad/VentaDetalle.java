package com.proy.v1.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ventadetalle")
public class VentaDetalle {// detalle de la venta
    @Id
    @Column(length = 10)
    private String numerodetalle; // numero detalle
    
    @ManyToOne()
    @JoinColumn(name ="venta_numero")
    private Venta venta; //venta
    
    @ManyToOne()
    @JoinColumn(name ="productoid")
    private Producto producto; //codigo producto
    
    private int cantidad; // cantidad producto
    private double total; // precio producto * cantidad

    public VentaDetalle() {
    }

    public VentaDetalle(String numerodetalle, Venta venta, Producto producto, int cantidad, double total) {
        this.numerodetalle = numerodetalle;
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getNumerodetalle() {
        return numerodetalle;
    }

    public void setNumerodetalle(String numerodetalle) {
        this.numerodetalle = numerodetalle;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
