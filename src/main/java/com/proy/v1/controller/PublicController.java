package com.proy.v1.controller;

import com.proy.v1.entidad.Usuario;
import com.proy.v1.entidad.VentaDetalle;
import com.proy.v1.service.DistritosService;
import com.proy.v1.service.ProductoService;
import com.proy.v1.service.UsuarioService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicController {
    @Autowired
    ProductoService prServ;
    @Autowired
    UsuarioService usServ;
    @Autowired
    DistritosService disServ;
    List<VentaDetalle> tempCarrito;

    @RequestMapping("/index")
    public String index() {
        return "/Public/index";
    }
    @RequestMapping("/productos")
    public String producto(Model model) {
        model.addAttribute("listaProducto", prServ.productoSel());
        return "/Public/productos";
    }
    @RequestMapping("/nuevoUsuario")
    public String usuarioForm(Model model, Usuario usuario) {
        model.addAttribute("dis", disServ.DistritosSel());
        return "/Public/agregarUsuario";
    }
    @RequestMapping("/login")
    public String login(){
        return "/Public/login";
    }
    @PostMapping("/usuarioIns")
    public String usuarioIns(Model model, @Valid Usuario usuario, BindingResult result) {
        try {
            model.addAttribute("dis", disServ.DistritosSel());
            if (result.hasErrors()) {
                return "redirect:/public/index";
            }
            usServ.usuarioInsUpd(usuario, 1);
            return "redirect:/public/index";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Ruc duplicado");
            return "redirect:/public/index";
        }
    }
    
}
