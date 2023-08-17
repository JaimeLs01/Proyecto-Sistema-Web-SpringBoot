    package com.proy.v1.dao;

import com.proy.v1.entidad.VentaDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DaoVentaDetalle extends JpaRepository<VentaDetalle, Integer>{
    @Query( value = "select * from ventadetalle where venta_numero = :nombre", nativeQuery = true )
    List<VentaDetalle> ListarDetalle(@Param("nombre")String nombre);
    @Query( value = "SELECT SUM(v.cantidad) as value,p.nombre as name FROM ventadetalle v inner join producto p on v.productoid=p.productoid group by v.productoid order by SUM(v.cantidad) desc LIMIT 5;", nativeQuery = true )
    List<Test2> obtenerProductosMasVendidos();
    
    @Query( value = "SELECT IFNULL(ROUND(SUM(v.cantidad), 2), 0) as total_ventas FROM ventadetalle v WHERE v.productoid =:productoid", nativeQuery = true)
    Integer obtenerTotal(@Param("productoid")int productoid);
    
    
    
}
