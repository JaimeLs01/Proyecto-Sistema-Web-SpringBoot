package com.proy.v1.controller;

import com.proy.v1.dao.Test;
import com.proy.v1.dao.Test2;
import com.proy.v1.entidad.Inventario;
import com.proy.v1.entidad.Producto;

import com.proy.v1.entidad.Usuario;
import com.proy.v1.entidad.VentaDetalle;
import com.proy.v1.service.InventarioService;
import com.proy.v1.service.ProductoService;
import com.proy.v1.service.UsuarioService;
import com.proy.v1.service.VentaDetalleService;
import com.proy.v1.service.VentaService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class RestControlador {

    @Autowired
    VentaService vntServ;
    @Autowired
    VentaDetalleService dvntServ;
    @Autowired
    UsuarioService usServ;
    @Autowired
    InventarioService invServ;
    @Autowired
    ProductoService proServ;

    @RequestMapping("/cantidadProductosVendidos")
    public List<Producto> cantidadProductosVendidos() {
        
        List<VentaDetalle> productos = dvntServ.ventaSel();
        List<Producto> listaproductos = proServ.productoSel();
        for (int i = 0; i < listaproductos.size(); i++) {
            listaproductos.get(i).setStock(0);
        }
        for (int i = 0; i < productos.size(); i++) {//itera por la cantidad de productos registrados por venta
            int idProductoAux = productos.get(i).getProducto().getProductoId();
            for (int x = 0; x < listaproductos.size(); x++) {//itera por la cantidad de productos almacenados
                int idProducto = listaproductos.get(x).getProductoId();
                if (idProducto == idProductoAux) {
                    Producto productoAux = listaproductos.get(x);
                    productoAux.setStock(productos.get(i).getCantidad() + productoAux.getStock());
                    listaproductos.set(x, productoAux);
                }
            }
        }
        return listaproductos;
    }

    @RequestMapping("/productosIngresados")
    public List<Integer> productoIngresados() {
        List<Integer> lista = new ArrayList<>();
        Calendar calendario2 = Calendar.getInstance();
        int mesActual = calendario2.get(Calendar.MONTH) + 1;
        lista.add(getProductosIngresadosMes(mesActual));
        lista.add(getProductosIngresadosMes(mesActual - 1));
        return lista;
    }


    public Integer getProductosIngresadosMes(int mesActual) {
        List<Inventario> inventario = invServ.inventarioSel();
        Integer reporte = 0;
        for (int i = 0; i < inventario.size(); i++) {
            String accion = inventario.get(i).getAccion();
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(inventario.get(i).getFecha());
            int mes = calendario.get(Calendar.MONTH) + 1;

            if (accion.equals("Registro") && mes == mesActual) {
                reporte++;
            }
        }
        return reporte;
    }


    @RequestMapping("/obtenerResumenVentasMensuales")
    public List<Test> obtenerResumenVentasMensuales() {
        return vntServ.obtenerResumenVentasMensuales();
    }

    @RequestMapping("/obtenerProductosMasVendidos")
    public List<Test2> obtenerProductosMasVendidos() {
        return dvntServ.obtenerProductosMasVendidos();
    }

    @RequestMapping("/obtenerResumenVentasUltimosMeses")
    public List<Test> obtenerResumenVentasUltimosMeses() {
        return vntServ.obtenerResumenVentasUltimosMeses();
    }

    @RequestMapping("/g_metaDiaria")
    public Double g_metaDiaria() {
        return vntServ.ventasDiarias();
    }


}
