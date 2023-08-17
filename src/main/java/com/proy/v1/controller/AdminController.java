/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proy.v1.controller;

import com.lowagie.text.DocumentException;
import com.proy.v1.entidad.Inventario;
import com.proy.v1.entidad.ListarVentasPdf;
import com.proy.v1.entidad.Producto;
import com.proy.v1.entidad.Usuario;
import com.proy.v1.entidad.VentaDetalle;
import com.proy.v1.service.InventarioService;
import com.proy.v1.service.ProductoService;
import com.proy.v1.service.UsuarioService;
import com.proy.v1.service.VentaDetalleService;
import com.proy.v1.service.VentaService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/administrador")
public class AdminController {

    @Autowired
    UsuarioService usServ;
    @Autowired
    ProductoService prServ;
    @Autowired
    InventarioService invServ;
    @Autowired
    VentaService vntServ;
    @Autowired
    VentaDetalleService detServ;
    String nombreUsuario = "Jaime";

    @RequestMapping("/listProducto")
    public String listProducto(Producto producto, Model model) {
        model.addAttribute("listProducto", prServ.productoSel());
        model.addAttribute("usuarioNombre", nombreUsuario);
        return "/Administrador/listProducto";
    }

    @RequestMapping("/listarUsuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("listaUsuarios", usServ.usuarioSel());
        model.addAttribute("usuarioNombre", nombreUsuario);
        return "/Administrador/listarUsuarios";
    }

    @RequestMapping("/kardex")
    public String kardex(Model model) {
        model.addAttribute("listaKardex", invServ.inventarioSel());
        model.addAttribute("usuarioNombre", nombreUsuario);
        return "/Administrador/kardex";
    }

    @RequestMapping("/graficos")
    public String graficos(Model model) {
        double totalVentas = Math.round(vntServ.obtenertotal() * 100.0) / 100.0;
        model.addAttribute("usuarioNombre", nombreUsuario);
        model.addAttribute("total_ventas", totalVentas);
        model.addAttribute("total_pedidos", vntServ.listarventa().size());
        model.addAttribute("total_usuarios", usServ.usuarioSel().size());
        model.addAttribute("total_productos", prServ.productoSel().size());
        model.addAttribute("cantidadClientes", vntServ.cantidadClientes());
        model.addAttribute("cantidadClientesPotenciales", vntServ.cantidadClientesPotenciales());
        return "/Administrador/graficos";
    }

    @RequestMapping("/usuarioAct")
    public String productoAct(Usuario usuario, Integer id) {

        usuario = usServ.usuarioGet(id);
        if (usuario.getEstado() == null) {
            usuario.setEstado(1);
        } else {
            usuario.setEstado(null);
        }
        usServ.usuarioInsUpd(usuario, 0);
        return "redirect:/administrador/listarUsuarios";
    }


    @RequestMapping("/VerVentas")
    public String VisualizarVentas(Model model){
        model.addAttribute("ventas", vntServ.ventaSel());

        return "/Administrador/listaVentas";
    }
    @RequestMapping("/actualizarProducto")
    public String actualizarProducto(Model model, int id) {
        model.addAttribute("producto", id);
        return "/Administrador/actualizarProducto";
    }
    @RequestMapping("/productosUdp")
    public String productoUpd(HttpServletRequest request, Model model, @RequestParam int id) {
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        model.addAttribute("lista2", usServ.buscarUsuarioNombre(nombreUsuario));
        Producto producto = prServ.productoget(id);
        java.util.Date fecha = new Date();
        String descripcion = "El producto" + producto.getNombre() + " con Stock inicial:" + producto.getStock() + "Cantidad agregada: " + cantidad + "Stock final:" + producto.getStock() + cantidad;
        Inventario inventario = new Inventario(producto, "Agrego", cantidad, descripcion, fecha);
        producto.setStock(producto.getStock() + cantidad);
        prServ.productoInsUpd(producto);
        invServ.inventarioInsUpd(inventario);
        return "redirect:listaProductos";
    }
    @GetMapping("/exportarPDFdetalleVenta")
    public void exportarListadoDeDetalleVentaEnPDF(HttpServletResponse response, String nombre) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Documento_Ventas_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<VentaDetalle> detalleventa = detServ.verDetalleVenta(nombre);

        ListarVentasPdf exporter = new ListarVentasPdf(detalleventa);

        exporter.exportar(response);

    }

}
