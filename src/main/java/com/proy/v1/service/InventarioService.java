
package com.proy.v1.service;

import com.proy.v1.dao.DaoInventario;
import com.proy.v1.entidad.Inventario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {
    @Autowired
    DaoInventario us;
    public List<Inventario> inventarioSel(){
       return us.findAll();
    }
    public void inventarioInsUpd(Inventario p){
        us.save(p);
    }
    public void inventarioDel(Inventario id){
       us.delete(id);
    }
}
