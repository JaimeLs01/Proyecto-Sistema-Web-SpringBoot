/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proy.v1.service;

import com.proy.v1.dao.DaoUsuario;
import com.proy.v1.entidad.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService{
    @Autowired
    DaoUsuario serviceUsuario;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = serviceUsuario.findByNombre(username);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USER"));

        UserDetails userDetails = new User(usuario.getNombre(),usuario.getContrasena(),roles);

        return userDetails;
    } 
    
    public List<Usuario> usuarioSel(){//mostrar
       return serviceUsuario.findAll();
   }
   public Usuario usuarioGet(Integer ruc){
       //return serviceUsuario.getById(ruc);
       return serviceUsuario.getById(ruc);
   }
   public Usuario buscarUsuarioNombre(String nombre){
       //return serviceUsuario.getById(ruc);
       return serviceUsuario.findByNombre(nombre);
   }
   public List<Usuario> UsuariosActivos(){
       return serviceUsuario.UsuarioActivo();
   }
   public void usuarioInsUpd(Usuario usuario,int bandera){//insertar modificar
       if(bandera==1){
           usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
       }
       serviceUsuario.save(usuario);
   }
}
