/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proy.v1.dao;


import com.proy.v1.entidad.Venta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DaoVenta extends JpaRepository<Venta, Integer>{
    @Query( value = "select v.usuario_id,v.numero,u.nombre,u.correo,v.fecha,ROUND(v.total, 2) as total from venta v inner join usuario u on v.usuario_id=u.id where u.nombre =:filtro1", nativeQuery = true )
    List<Venta> ObtenerVenta(@Param("filtro1")String filtro1);
    
    @Query( value = "SELECT COALESCE(SUM(total), 0) FROM venta;", nativeQuery = true )
    Double ObtenerTotalVenta();
    
    @Query( value = "SELECT v.usuario_id,v.numero,u.nombre,u.correo,v.fecha,ROUND(v.total, 2) as total FROM venta v inner join usuario u on v.usuario_id=u.id order by v.fecha desc;", nativeQuery = true )
    List<Venta> Listarventa();
    
    
    //@Query( value = "SELECT ROUND( SUM(v.total),2) AS Ventas FROM venta v GROUP BY MONTH(v.fecha)", nativeQuery = true )
    @Query( value = "SELECT ROUND( SUM(v.total),2) AS Ventas,MONTHNAME(v.fecha) as mes FROM venta v GROUP BY MONTHNAME(v.fecha);", nativeQuery = true )
    List<Test> obtenerResumenVentasMensuales();
    
    
    @Query( value = "SELECT ROUND( SUM(v.total),2) AS Ventas,v.fecha as meses FROM venta v GROUP BY v.fecha order by v.fecha desc limit 5", nativeQuery = true )
    List<Test> obtenerResumenVentasUltimosMeses();
    
    @Query( value = "SELECT COUNT(DISTINCT usuario_id) AS cantidadClientes FROM venta;", nativeQuery= true)
    Integer cantidadClientes();
    
    @Query( value = "SELECT COUNT(DISTINCT usuario_id) AS cantidad_clientes FROM venta WHERE fecha >= DATE_SUB(CURDATE(), INTERVAL 30 DAY);", nativeQuery= true)
    Integer cantidadClientesPotenciales();
    
    @Query( value = "SELECT COALESCE(SUM(total), 0) as venta_diaria FROM venta WHERE fecha = CURDATE() ", nativeQuery= true)
    Double ventasDiarias();
    
    
}