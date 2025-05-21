package com.salesianostriana.dam.GarciaMariaPablo.global.seguridad;

import com.salesianostriana.dam.GarciaMariaPablo.global.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class ServicioCustomUserDetails  implements UserDetailsService {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return servicioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado."));
    }
}
