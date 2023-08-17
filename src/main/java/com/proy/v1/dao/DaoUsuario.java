/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proy.v1.dao;

import com.proy.v1.entidad.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



public interface DaoUsuario extends JpaRepository<Usuario, Integer>{
    Usuario findByNombre(String nombre);
    @Query(value ="select * from usuario where usuario.estado is null",nativeQuery = true)
    List<Usuario> UsuarioActivo();
    /*@Query(value ="select * from producto where productoid > 9",nativeQuery = true)
    public Usuario find(String ruc);//se quiere que reciba solo un parámetro pero que realice la búsqueda en los dos campos*/
}
