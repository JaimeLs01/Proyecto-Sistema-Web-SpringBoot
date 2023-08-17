package com.proy.v1.entidad;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="venta")
public class Venta { //venta
    @Id
    @Column(length = 8)
    private String numero; //numero de venta
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="usuario_id")
    private Usuario usuario; // cliente

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha; // fecha de la venta
    private double total; // monto total

    public Venta() {
    }

    public Venta(String numero, Usuario usuario, Date fecha, double total) {
        this.numero = numero;
        this.usuario = usuario;
        this.fecha = fecha;
        this.total = total;
    }



    public String getNumero() {
        return numero;
    }

    public void setNumero(String numeroventa) {
        this.numero = numeroventa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
