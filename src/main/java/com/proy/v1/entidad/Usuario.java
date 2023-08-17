
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")     
    private Integer id;
    
    @NotBlank(message="*Campo Vacio")
    @Column(name = "nombre")
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "*Solo se permiten letras y números")
    private String nombre;
    
    @NotBlank(message = "Ingrese contraseña")
    @Column(name = "contrasena")  
    private String contrasena;
    
    @NotBlank(message = "*Campo Vacio")
    @Column(name = "direccion")  
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "*Solo se permiten letras y números")
    private String direccion;
    
    @NotBlank(message = "*Campo Vacio")
    @Column(name = "telefono")  
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "*Solo se permiten letras y números")
    private String telefono;
    
    @NotBlank(message = "*Campo Vacio")
    @Column(name = "correo") 
    @Email(message = "*Ingrese correo")
    private String correo;
    
    @Column(name = "estado") 
    private Integer estado;
    
    @ManyToOne()
    @JoinColumn(name ="id_distrito")
    private Distritos id_distrito;

    public Distritos getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(Distritos id_distrito) {
        this.id_distrito = id_distrito;
    }
    
//    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "usuario")
//    private Set<Guia> guias;

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

        public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

//    public Set<Guia> getGuias() {
//        return guias;
//    }
//
//    public void setGuias(Set<Guia> guias) {
//        this.guias = guias;
//    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    
}
