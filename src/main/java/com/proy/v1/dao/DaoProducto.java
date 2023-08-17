package com.proy.v1.dao;

import com.proy.v1.entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



public interface DaoProducto extends JpaRepository<Producto, Integer>{
    @Transactional
    @Modifying
    @Query(value = "update producto set stock=stock-:cantidad where productoid=:producto", nativeQuery = true)
    void actualizarStock(@Param("cantidad")int cantidad,@Param("producto")int producto);
    
}