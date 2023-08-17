
package com.proy.v1.dao;

import com.proy.v1.entidad.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DaoInventario extends JpaRepository<Inventario, Integer>{
    
}
