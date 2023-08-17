package com.proy.v1.service;

import com.proy.v1.dao.DaoVentaDetalle;
import com.proy.v1.dao.Test2;
import com.proy.v1.entidad.VentaDetalle;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaDetalleService {
    @Autowired
    DaoVentaDetalle us;
    public List<VentaDetalle> ventaSel(){
       return us.findAll();
    }
    public void ventaDetalleInsUpd(VentaDetalle p){
        us.save(p);
    }
    
    public List<VentaDetalle> verDetalleVenta(String ruc){
        return us.ListarDetalle(ruc);
    }
    public List<Test2> obtenerProductosMasVendidos(){
        return us.obtenerProductosMasVendidos();
    }
    public Integer obtenerTotal(int id){
        return us.obtenerTotal(id);
    }
}
