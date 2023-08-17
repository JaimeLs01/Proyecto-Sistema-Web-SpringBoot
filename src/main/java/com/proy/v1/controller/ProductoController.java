/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proy.v1.controller;

import com.proy.v1.entidad.Inventario;
import com.proy.v1.entidad.Producto;
import com.proy.v1.service.InventarioService;
import com.proy.v1.service.ProductoService;
import com.proy.v1.service.UsuarioService;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    UsuarioService usServ;
    @Autowired
    ProductoService prServ;
    @Autowired
    InventarioService invServ;

    @PostMapping("/productoIns")
    public String productoIns(Model model, @Valid Producto producto) {
        prServ.productoInsUpd(producto);
        java.util.Date fecha = new Date();
        String descripcion = "Registro el producto" + producto.getNombre() + "con el stock " + producto.getStock();
        Inventario inventario = new Inventario(producto, "Registro", producto.getStock(), descripcion, fecha);
        invServ.inventarioInsUpd(inventario);
        return "redirect:/administrador/listProducto";
    }
}
