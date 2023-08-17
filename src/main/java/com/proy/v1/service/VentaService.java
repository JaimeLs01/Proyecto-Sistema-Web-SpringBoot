package com.proy.v1.service;

import com.proy.v1.dao.DaoVenta;
import com.proy.v1.dao.Test;
import com.proy.v1.entidad.Venta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {
    @Autowired
    DaoVenta us;
    
    public List<Venta> ventaSel(){
       return us.findAll();
   }
   public List<Venta> ventaget(String id){
       return us.ObtenerVenta(id);
   }
   public void ventaInsUpd(Venta p){
       p.setTotal(Math.round(p.getTotal()));
       us.save(p);
   }
   public Double obtenertotal(){
       return us.ObtenerTotalVenta();
   }
   public List<Test> obtenerResumenVentasMensuales(){
       return us.obtenerResumenVentasMensuales();     
   }
   public List<Venta> listarventa(){
       return us.Listarventa();
   }
   public List<Test> obtenerResumenVentasUltimosMeses(){
       return us.obtenerResumenVentasUltimosMeses();
   }
   public Integer cantidadClientes(){
       return us.cantidadClientes();
   }
   public Integer cantidadClientesPotenciales(){
       return us.cantidadClientesPotenciales();
   }
   public Double ventasDiarias(){
       return us.ventasDiarias();
   }
}

