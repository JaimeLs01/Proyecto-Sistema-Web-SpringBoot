package com.proy.v1.service;

import com.proy.v1.dao.DaoProducto;
import com.proy.v1.entidad.Producto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    @Autowired
    DaoProducto serviceProducto;
    public List<Producto> productoSel(){
       return serviceProducto.findAll();
   }
   public Producto productoget(Integer id){
       return serviceProducto.getById(id);
   }
   public void productoInsUpd(Producto producto){
       serviceProducto.save(producto);
   }
   public void productoDel(Integer id){
       serviceProducto.deleteById(id);
   }
   public void actualizarStock(int nuevoStock,int idproducto){
       serviceProducto.actualizarStock(nuevoStock, idproducto);
   }
}
