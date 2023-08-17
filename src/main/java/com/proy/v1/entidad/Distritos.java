
package com.proy.v1.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "distritos")
public class Distritos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distrito")
    private Integer id_distrito;
    
    @NotBlank(message = "*Campo vacio")
    @Column(name = "nombre_distrito")
    private String nombre_distrito;

    public Distritos() {
    }

    public Integer getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(Integer id_distrito) {
        this.id_distrito = id_distrito;
    }

    public String getNombre_distrito() {
        return nombre_distrito;
    }

    public void setNombre_distrito(String nombre_distrito) {
        this.nombre_distrito = nombre_distrito;
    }
    
    
    
}
