/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proy.v1;

import com.proy.v1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioService service;

    @Autowired
    BCryptPasswordEncoder cripto;//959

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(cripto);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/imagenes/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        /*http.authorizeRequests().antMatchers("/assets/icons", "/static/**", "/css/**", "/fonts/**", "/imagenes/**", "/js/**", "/", "/usuarioIns", "/loginadmin", "/Productos", "/nuevoUsuario", "/index", "/Productos", "/actualizarUsuario", "/usuarioUpd").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/welcome")
                .failureUrl("/login?error=true")
                //.usernameParameter("username")
                //.usernameParameter("password")
                .and().headers()
                .frameOptions()
                .disable()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
        //   .permitAll().logoutSuccessUrl("/login?logout").deleteCookies(cookieNamesToClear);*/
//}
                http.authorizeRequests()
                        .antMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                .and().headers()
                .frameOptions()
                .disable()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
                http.formLogin(form -> form
			.loginPage("/public/login")
			.permitAll()
                        .successHandler((request, response, authentication) -> {
                            String username = authentication.getName();
                            if(username.equals("admin")){
                                response.sendRedirect("/administrador/graficos");
                            }else{
                                response.sendRedirect("/usuario/welcome");
                            }
                        })
                );

    }

}
