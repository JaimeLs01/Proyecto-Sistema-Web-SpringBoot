
package com.proy.v1.service;

import com.proy.v1.dao.DaoDistritos;
import com.proy.v1.entidad.Distritos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistritosService {
    @Autowired
    DaoDistritos serviceDistritos;
    
    
    public List<Distritos> DistritosSel(){
       return serviceDistritos.findAll();
   }
}
