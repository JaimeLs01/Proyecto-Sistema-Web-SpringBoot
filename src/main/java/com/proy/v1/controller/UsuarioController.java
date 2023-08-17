package com.proy.v1.controller;

import com.lowagie.text.DocumentException;
import com.proy.v1.entidad.Inventario;
import com.proy.v1.entidad.ListarVentasPdf;
import com.proy.v1.entidad.Producto;
import com.proy.v1.entidad.Usuario;
import com.proy.v1.entidad.Venta;
import com.proy.v1.entidad.VentaDetalle;
import com.proy.v1.service.InventarioService;
import com.proy.v1.service.ProductoService;
import com.proy.v1.service.UsuarioService;
import com.proy.v1.service.VentaDetalleService;
import com.proy.v1.service.VentaService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    String nombreUsuario;
    List<VentaDetalle> tempCarrito;
    Producto productoCarrito;
    @Autowired
    UsuarioService usServ;
    @Autowired
    ProductoService prServ;
    @Autowired
    VentaService vntServ;
    @Autowired
    InventarioService invServ;
    @Autowired
    VentaDetalleService detServ;

    @RequestMapping("/welcome")
    public String welcome(Authentication auth, Model model) {
        nombreUsuario = auth.getName();
        if (tempCarrito != null) {
            model.addAttribute("carrito", tempCarrito.size());
        }
        return "/Usuario/indexBienvenido";
    }

    @RequestMapping("/VerCompras")
    public String VerCompras(Model model) {
        model.addAttribute("ventas", vntServ.ventaget(nombreUsuario));
        return "/Usuario/listarcompras";
    }
    @GetMapping("/VenderProducto")
    public String VenderProducto(HttpServletRequest request, Model model) {
        int cod = Integer.parseInt(request.getParameter("codigo"));
        productoCarrito = prServ.productoget(cod);
        model.addAttribute("producto", prServ.productoget(cod));
        return "/Usuario/venderproducto";
    }

    @PostMapping("/AnadirCompra")
    public String AnadirCarrito(Model model, int cantidad) {
        int idProductoCarrito = productoCarrito.getProductoId();
        if (tempCarrito != null && cantidad > 0 && cantidad <= productoCarrito.getStock()) {

            for (int i = 0; i < tempCarrito.size(); i++) {
                int id_producto = tempCarrito.get(i).getProducto().getProductoId();
                if (id_producto == idProductoCarrito) {
                    VentaDetalle temp = tempCarrito.get(i);
                    temp.setCantidad(cantidad);
                    tempCarrito.set(i, temp);
                    productoCarrito = null;
                    model.addAttribute("detalles", tempCarrito);
                    model.addAttribute("total", VentaTotal(tempCarrito));
                    return "redirect:/usuario/VerCarrito";
                }
            }
        }
        if (cantidad > 0 && cantidad <= productoCarrito.getStock()) { //50 <= 40
            String numero;
            int orden;
            double total = productoCarrito.getPrecioUnitario() * cantidad;
            total = Math.round(total * 100.0) / 100.0;
            Venta nueva = new Venta();
            if (tempCarrito == null) {
                numero = GenerarNumero();
                orden = 0;
                tempCarrito = new ArrayList();
            } else {

                numero = tempCarrito.get(0).getVenta().getNumero();
                orden = tempCarrito.size();
            }
            orden++;
            nueva.setNumero(numero);
            String numeroDetalle = numero + orden;
            VentaDetalle nuevoProducto = new VentaDetalle(numeroDetalle, nueva, productoCarrito, cantidad, total);
            tempCarrito.add(nuevoProducto);
            productoCarrito = null;
            model.addAttribute("detalles", tempCarrito);
            model.addAttribute("total", VentaTotal(tempCarrito));
            return "redirect:/usuario/VerCarrito";
        } else {
            model.addAttribute("errorStock", "No hay suficiente stock.");
            return "/Usuario/indexBienvenido";
        }

    }

    @GetMapping("/VerCarrito")
    public String VerCarrito(Model model) {
        model.addAttribute("detalles", tempCarrito);
        if (tempCarrito != null) {
            model.addAttribute("total", VentaTotal(tempCarrito));
        }
        model.addAttribute("listaProducto", prServ.productoSel());
        return "/Usuario/carrito";
    }

    @GetMapping("/SacarProducto")
    public String SacarProducto(HttpServletRequest request, Model model) {
        String numero = request.getParameter("codi");
        if (tempCarrito != null) {
            for (int i = 0; i < tempCarrito.size(); i++) {
                if (tempCarrito.get(i).getNumerodetalle().equals(numero)) {
                    tempCarrito.remove(tempCarrito.get(i));
                }
            }
            if (tempCarrito.isEmpty()) {
                tempCarrito = null;
            } else {
                for (int i = 0; i < tempCarrito.size(); i++) {
                    tempCarrito.get(i).setNumerodetalle(tempCarrito.get(i).getVenta().getNumero() + (i + 1));
                }
            }
        }
        model.addAttribute("detalles", tempCarrito);
        if (tempCarrito != null) {
            model.addAttribute("total", VentaTotal(tempCarrito));
        }
        return "redirect:/usuario/VerCarrito";

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
    @RequestMapping("/FinalizarVenta")
    public String FinalizarVenta(Model model) {
        Venta nueva = new Venta();
        Usuario usuario = usServ.buscarUsuarioNombre(nombreUsuario);
        java.util.Date fecha = new Date();
        nueva.setFecha(fecha);
        nueva.setUsuario(usuario);
        if (tempCarrito != null) {
            nueva.setTotal(VentaTotal(tempCarrito));
            nueva.setNumero(tempCarrito.get(0).getVenta().getNumero());
            vntServ.ventaInsUpd(nueva);

            for (int i = 0; i < tempCarrito.size(); i++) {
                String nombreProducto = tempCarrito.get(i).getProducto().getNombre();
                int cantidad = tempCarrito.get(i).getCantidad();
                Producto producto = tempCarrito.get(i).getProducto();
                String descripcion = "Compraron el producto" + nombreProducto + "la cantidad de " + cantidad;
                Inventario inventario = new Inventario(producto, "Salida", cantidad, descripcion, fecha);
                invServ.inventarioInsUpd(inventario);
                int idProducto = tempCarrito.get(i).getProducto().getProductoId();
                int stock = tempCarrito.get(i).getCantidad();
                tempCarrito.get(i).setVenta(nueva);
                detServ.ventaDetalleInsUpd(tempCarrito.get(i));
                prServ.actualizarStock(stock, idProducto);

            }

            tempCarrito.removeAll(tempCarrito);
            tempCarrito = null;
        }
        model.addAttribute("listaProducto", prServ.productoSel());
        return "/Public/productos";
    }

    private double VentaTotal(List<VentaDetalle> venta) {
        double suma = 0;
        if (venta != null) {
            for (int i = 0; i < venta.size(); i++) {
                suma += venta.get(i).getTotal();
            }
        }
        suma = Math.round(suma * 100.0) / 100.0;
        return suma;
    }

    private String GenerarNumero() {
        List<Venta> lista = vntServ.ventaSel();
        int numero;
        if (lista.isEmpty()) {
            numero = 10000000;
        } else {
            numero = Integer.parseInt(lista.get(lista.size() - 1).getNumero()) + 1;
        }
        return "" + numero;
    }

}
